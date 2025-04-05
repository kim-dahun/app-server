package com.service.discovery.api.service.impl;

import com.service.core.constants.MessageConstants;
import com.service.core.exception.CustomRuntimeException;
import com.service.core.service.MessageService;
import com.service.core.vo.response.CmnResponseVo;
import com.service.discovery.api.service.ApiGateWayService;
import com.service.discovery.constants.HttpConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.service.core.constants.MessageConstants.*;
import static com.service.discovery.constants.HttpConstants.X_FORWARDED_FOR;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
@RequiredArgsConstructor
public class ApiGateWayServiceImplV1 implements ApiGateWayService {

    public final Map<String, List<String>> servicePortMap = new ConcurrentHashMap<>();
    public final Map<String, AtomicInteger> serviceRoundRobinIndexMap = new ConcurrentHashMap<>();

    private final WebClient.Builder webClientBuilder;

    private final MessageService messageService;

    @Override
    public Mono<ResponseEntity<CmnResponseVo>> api_doHttpRequest(ServerHttpRequest request, Mono<byte[]> body) throws IOException {
        String[] urlPatterns = request.getURI().getPath().split("/");
        String langCode = request.getHeaders().getFirst(ACCEPT_LANGUAGE).contains("ko") ? KO : EN;
        if(urlPatterns.length < 2){
            throw new CustomRuntimeException(messageService.getMessage(langCode,null, SEARCH_FAIL),404);
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

        HttpHeaders headers = request.getHeaders();

        headers.remove(HttpHeaders.HOST);

        return webClient.method(request.getMethod())
                .uri(fullPath)
                .headers((httpHeaders -> httpHeaders.addAll(headers)))
                .body(body!=null ? BodyInserters.fromPublisher(body, byte[].class) : BodyInserters.empty())
                .exchangeToMono((response) -> response.toEntity(CmnResponseVo.class));

    }

    @Override
    public Boolean isAuth(ServerHttpRequest request, Mono<byte[]> body)  {
        return null;
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
    public Boolean api_insertRequestHistory(ServerHttpRequest request) {
        return null;
    }

    @Override
    public Boolean isBadRequest(ServerHttpRequest request) {
        return null;
    }
}
