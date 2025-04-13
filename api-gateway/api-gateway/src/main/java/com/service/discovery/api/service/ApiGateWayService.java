package com.service.discovery.api.service;

import com.service.core.vo.response.CmnResponseVo;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ApiGateWayService {



    Mono<ResponseEntity<CmnResponseVo>> api_doHttpRequest(ServerHttpRequest request, Mono<byte[]> body) throws IOException;

    String getApiUrl(String serviceName);

    void setApiUrl(String serviceName, String apiUrl);

    Mono<Boolean> api_insertRequestHistory(String ip, String deviceNm, String method, String endPoint, String token);

    boolean isBadRequest(String[] urlPatterns);

}
