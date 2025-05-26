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
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "http://127.0.0.1:5173", allowCredentials = "true", maxAge = 3600)
@RequestMapping(ApiConstants.API_BASE)
public class ApiGateWayController {

    private final ApiGateWayService apiGateWayService;

    @RequestMapping
    public Mono<ResponseEntity<CmnResponseVo>> api_doHttpRequest(ServerHttpRequest request, Mono<byte[]> body) throws IOException {
        return apiGateWayService.api_doHttpRequest(request, body);
    }


}
