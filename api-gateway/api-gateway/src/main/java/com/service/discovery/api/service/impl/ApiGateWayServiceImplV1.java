package com.service.discovery.api.service.impl;

import com.service.core.config.webMvc.GlobalRestControllerAdvice;
import com.service.core.exception.CustomRuntimeException;
import com.service.core.service.MessageService;
import com.service.core.util.ConverterUtils;
import com.service.core.util.NativeQueryUtils;
import com.service.core.vo.response.CmnResponseVo;
import com.service.discovery.api.service.ApiGateWayService;
import com.service.discovery.constants.HttpConstants;
import com.service.discovery.entity.RequestHistory;
import com.service.discovery.repository.RequestHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.service.core.constants.MessageConstants.*;
import static com.service.core.constants.MessageConstants.EN;
import static com.service.discovery.constants.HttpConstants.X_FORWARDED_FOR;
import static org.springframework.http.HttpHeaders.*;

@Service
@RequiredArgsConstructor
public class ApiGateWayServiceImplV1 implements ApiGateWayService {

    public final Map<String, List<String>> servicePortMap = new ConcurrentHashMap<>();
    public final Map<String, AtomicInteger> serviceRoundRobinIndexMap = new ConcurrentHashMap<>();

    private final WebClient.Builder webClientBuilder;

    private final RequestHistoryRepository requestHistoryRepository;

    private final String AUTH_MANAGE_SERVER = "auth-manage-server";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<ResponseEntity<CmnResponseVo>> api_doHttpRequest(ServerHttpRequest request, Mono<byte[]> body) throws IOException {
        String[] urlPatterns = request.getURI().getPath().split("/");
        String langCode = request.getHeaders().getFirst(HttpConstants.ACCEPT_LANGUAGE).contains("ko") ? KO : EN;
        if(isBadRequest(urlPatterns)){
            throw new CustomRuntimeException(SEARCH_FAIL,404);
        }
        String userAgent = request.getHeaders().getFirst(USER_AGENT);
        String clientIp = request.getHeaders().getFirst(X_FORWARDED_FOR);
        String serviceName = urlPatterns[2];
        String baseUrl = getApiUrl(serviceName);
        WebClient webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
        urlPatterns[0] = "";
        String fullPath = String.join("/",urlPatterns);
        String token = "";
        HttpHeaders headers = request.getHeaders();
        return api_insertRequestHistory(clientIp, userAgent, request.getMethod().name(), fullPath, token)
                .flatMap(inserted -> {
                    if (!inserted) {
                        return Mono.error(new CustomRuntimeException("데이터 전달 중 에러 발생"));
                    }

                    return webClient.method(request.getMethod())
                            .uri(fullPath)
                            .headers(httpHeaders -> httpHeaders.addAll(headers))
                            .body(body != null ? BodyInserters.fromPublisher(body, byte[].class) : BodyInserters.empty())
                            .exchangeToMono(response -> response.toEntity(CmnResponseVo.class));
                });

    }

    @Override
    public String getApiUrl(String serviceName) {
        List<String> ports = servicePortMap.get(serviceName);
        if(ports == null){
            return null;
        }

        int index = getRoundRobinIndex(serviceName, ports);
        return ports.get(index);
    }

    private int getRoundRobinIndex(String serviceName, List<String> ports) {
        return serviceRoundRobinIndexMap.get(serviceName) == null || serviceRoundRobinIndexMap.get(serviceName).get() >= (ports.size() - 1) ? 0 : serviceRoundRobinIndexMap.get(serviceName).getAndAdd(1) ;
    }

    @Override
    public void setApiUrl(String serviceName, String apiUrl) {
        List<String> ports = servicePortMap.get(serviceName);
        if(ports==null){
            return;
        }
        ports.add(apiUrl);
        servicePortMap.put(serviceName, ports);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Boolean> api_insertRequestHistory(String ip, String deviceNm, String method, String endPoint, String token) {
        LocalDateTime now = LocalDateTime.now();
        String timekey = ConverterUtils.getTimeKeyMillSecond(now);

        return requestHistoryRepository.save(
                        RequestHistory.builder()
                                .requestId(timekey+"_"+ip)
                                .clientIp(ip)
                                .clientDeviceNm(deviceNm)
                                .requestEndPoint(endPoint)
                                .requestType(method)
                                .requestToken(token)
                                .requestDateTime(now)
                                .build())
                .map(saved -> true)
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public boolean isBadRequest(String[] urlPatterns) {
        if(urlPatterns.length < 2){
            return true;
        }
        return false;
    }
}
