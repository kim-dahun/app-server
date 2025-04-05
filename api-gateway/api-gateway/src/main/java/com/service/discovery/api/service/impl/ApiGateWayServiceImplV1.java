package com.service.discovery.api.service.impl;

import com.service.core.vo.response.CmnResponseVo;
import com.service.discovery.api.service.ApiGateWayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ApiGateWayServiceImplV1 implements ApiGateWayService {

    public final Map<String, List<String>> servicePortMap = new ConcurrentHashMap<>();
    public final Map<String, Integer> serviceRoundRobinIndexMap = new ConcurrentHashMap<>();

    @Override
    public ResponseEntity<CmnResponseVo> api_doHttpRequest(HttpServletRequest request) {
        return webClient
                .method(HttpMethod.valueOf(request.getMethod()))
                .uri(targetUrl + (request.getQueryString() != null ? "?" + request.getQueryString() : ""))
                .headers(headers -> {
                    Collections.list(request.getHeaderNames()).forEach(headerName -> {
                        if (!headerName.equalsIgnoreCase("host")) {
                            headers.addAll(headerName, Collections.list(request.getHeaders(headerName)));
                        }
                    });
                })
                .bodyValue(request)  // Spring이 자동으로 요청 본문을 처리
                .retrieve()
                .toEntity(CmnResponseVo.class)
                .block();  // 동기 방식으로 처리
    }

    @Override
    public Boolean isAuth(HttpServletRequest request) {
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
        return serviceRoundRobinIndexMap.get(serviceName) == null || serviceRoundRobinIndexMap.get(serviceName) >= (ports.size() - 1) ? 0 : serviceRoundRobinIndexMap.get(serviceName) + 1;
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
    public Boolean api_insertRequestHistory(HttpServletRequest request) {
        return null;
    }

    @Override
    public Boolean isBadRequest(HttpServletRequest request) {
        return null;
    }
}
