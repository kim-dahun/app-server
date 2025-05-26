package com.service.discovery.api.controller;

import com.service.core.constants.ApiConstants;
import com.service.core.vo.response.CmnResponseVo;
import com.service.discovery.api.service.ApiGateWayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "http://127.0.0.1:5173", allowCredentials = "true", maxAge = 3600)
@RequestMapping(ApiConstants.API_BASE)
public class ApiGateWayController {

    private final ApiGateWayService apiGateWayService;

    @RequestMapping("/**")
    public Mono<ResponseEntity<CmnResponseVo>> api_doHttpRequest(ServerWebExchange exchange) throws IOException {
        ServerHttpRequest request = exchange.getRequest();
        Mono<byte[]> body = getBody(request);  // 여러 번 구독될 수 있으므로 캐시 추가
        return apiGateWayService.api_doHttpRequest(request, body);
    }

    private Mono<byte[]> getBody(ServerHttpRequest request) {
        return request
                .getBody()
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    return bytes;
                })
                .reduce(new byte[0], (prev, curr) -> {
                    byte[] result = new byte[prev.length + curr.length];
                    System.arraycopy(prev, 0, result, 0, prev.length);
                    System.arraycopy(curr, 0, result, prev.length, curr.length);
                    return result;
                })
                .mapNotNull(bytes -> bytes !=null && bytes.length > 0 ? bytes : null)
                .defaultIfEmpty(new byte[0])
                .cache();
    }


}
