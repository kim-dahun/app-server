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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class ApiGateWayServiceImplV1 implements ApiGateWayService {

    public final Map<String, List<String>> servicePortMap = new ConcurrentHashMap<>();
    public final Map<String, AtomicInteger> serviceRoundRobinIndexMap = new ConcurrentHashMap<>();

    private final WebClient.Builder webClientBuilder;

    private final RequestHistoryRepository requestHistoryRepository;


    @Override
    public Mono<ResponseEntity<CmnResponseVo>> api_getServerList(ServerHttpRequest request) {
        CmnResponseVo responseVo = new CmnResponseVo();
        responseVo.setCommonResultMap(servicePortMap);
        return Mono.just(ResponseEntity.ok(responseVo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<ResponseEntity<CmnResponseVo>> api_doHttpRequest(ServerHttpRequest request, Mono<byte[]> body) throws IOException {
        String[] urlPatterns = request.getURI().getPath().split("/");
        Object acceptLanguage = request.getHeaders().getFirst(HttpConstants.ACCEPT_LANGUAGE);
        String langCode = KO;
        if (acceptLanguage != null) {
            langCode = acceptLanguage.toString().contains("ko") ? KO : EN;
        }

        if(isBadRequest(urlPatterns)){
            throw new CustomRuntimeException(SEARCH_FAIL,404);
        }
        String userAgent = request.getHeaders().getFirst(USER_AGENT);
        // 클라이언트 IP 얻기
        String clientIp = request.getRemoteAddress() != null ?
                request.getRemoteAddress().getAddress().getHostAddress() :
                request.getHeaders().getFirst(X_FORWARDED_FOR);

        // IP가 없는 경우 기본값 설정
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = "127.0.0.1";
        }
        String serviceName = urlPatterns[2];
        String baseUrl = getApiUrl(serviceName);
        if(baseUrl == null){
            return Mono.error(new CustomRuntimeException(SEARCH_FAIL,404));
        }
        WebClient webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
        urlPatterns[0] = baseUrl;
        String fullPath = String.join("/",urlPatterns);
        String token = "";
        HttpHeaders headers = request.getHeaders();
        String finalClientIp = clientIp;
        return Mono.defer(() ->
                api_insertRequestHistory(finalClientIp, userAgent, request.getMethod().name(), fullPath, token)
                        .flatMap(inserted -> {
                            if (!inserted) {
                                return Mono.error(new CustomRuntimeException("데이터 전달 중 에러 발생"));
                            }

                            return webClient.method(request.getMethod())
                                    .uri(fullPath)
                                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                                    .body(body != null ? BodyInserters.fromPublisher(body, byte[].class) : BodyInserters.empty())
                                    .exchangeToMono(response -> {
                                        log.info("response: {}", response.statusCode());
                                        if (response.statusCode().is2xxSuccessful()) {
                                            return response.toEntity(CmnResponseVo.class)
                                                    .switchIfEmpty(Mono.just(ResponseEntity.ok(new CmnResponseVo()))); // 빈 응답일 경우 기본 응답 반환
                                        } else {
                                            return Mono.error(new CustomRuntimeException("서버 응답 오류: " + response.statusCode()));
                                        }
                                    })
                                    .onErrorResume(e -> {
                                        log.error("Error in WebClient exchange: ", e);
                                        CmnResponseVo errorResponse = new CmnResponseVo();
                                        errorResponse.setMessage(e.getMessage());
                                        return Mono.just(ResponseEntity.internalServerError().body(errorResponse));
                                    });
                        })
                        .onErrorResume(e -> {
                            log.error("Error during request processing: ", e);
                            CmnResponseVo errorResponse = new CmnResponseVo();
                            errorResponse.setMessage(e.getMessage());
                            return Mono.just(ResponseEntity.internalServerError().body(errorResponse));
                        })
        );

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
            ports = new LinkedList<>();
        }
        ports.add(apiUrl);
        servicePortMap.put(serviceName, ports);
        log.info("ServerList : \n\r {}", servicePortMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Boolean> api_insertRequestHistory(String ip, String deviceNm, String method, String endPoint, String token) {
        LocalDateTime now = LocalDateTime.now();
        String timekey = ConverterUtils.getTimeKeyMillSecond(now);

        RequestHistory requestHistory = RequestHistory.builder()
                .requestId(timekey+"_"+ip)
                .clientIp(ip)
                .clientDeviceNm(deviceNm)
                .requestEndPoint(endPoint)
                .requestType(method)
                .requestToken(token)
                .requestDateTime(now)
                .build();

        log.debug("Attempting to save request history: {}", requestHistory);

        return requestHistoryRepository.save(requestHistory)
                .doOnSubscribe(subscription ->
                        log.debug("Starting save operation for request ID: {}", requestHistory.getRequestId()))
                .doOnSuccess(saved ->
                        log.info("Successfully saved request history: {}", saved))
                .doOnError(error ->
                        log.error("Error saving request history: {}", error.getMessage()))
                .map(saved -> true)
                .onErrorResume(e -> {
                    log.error("Failed to save request history", e);
                    return Mono.just(false);
                })
                .doFinally(signalType ->
                        log.debug("Save operation completed with signal: {}", signalType));
    }

    @Override
    public boolean isBadRequest(String[] urlPatterns) {
        if(urlPatterns.length < 2){
            return true;
        }
        return false;
    }

    @Override
    public void deleteApiUrl(String serviceName, String serviceUrl) {
        List<String> urlList = servicePortMap.get(serviceName);
        if(urlList==null){
            return;
        }
        urlList.removeIf(url -> url.equals(serviceUrl));
        log.info("ServerList : \n\r {}", servicePortMap);
    }
}
