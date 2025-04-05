package com.service.discovery.api.service;

import com.service.core.vo.response.CmnResponseVo;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ApiGateWayService {



    Mono<ResponseEntity<CmnResponseVo>> api_doHttpRequest(ServerHttpRequest request, Mono<byte[]> body) throws IOException;

    Boolean isAuth(ServerHttpRequest request, Mono<byte[]> body);

    String getApiUrl(String serviceName);

    void setApiUrl(String serviceName, String apiUrl);

    Boolean api_insertRequestHistory(ServerHttpRequest request);

    Boolean isBadRequest(ServerHttpRequest request);

}
