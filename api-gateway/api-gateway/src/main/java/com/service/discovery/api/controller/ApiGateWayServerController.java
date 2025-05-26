package com.service.discovery.api.controller;

import com.service.core.constants.ApiConstants;
import com.service.core.vo.response.CmnResponseVo;
import com.service.discovery.api.service.ApiGateWayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "http://127.0.0.1", allowCredentials = "true", maxAge = 3600)
@RequestMapping(ApiConstants.API_BASE)
public class ApiGateWayServerController {

    private final ApiGateWayService apiGateWayService;

    @PostMapping("/set-server")
    public void api_setServer(ServerHttpRequest request) {
        Object serviceName = request.getHeaders().getFirst("Service-name");
        Object serviceUrl = request.getHeaders().getFirst("Service-url");
        if(serviceName != null && serviceUrl != null) {
            apiGateWayService.setApiUrl(serviceName.toString(), serviceUrl.toString());
        }
    }

    @DeleteMapping("/delete-server")
    public void api_deleteServer(ServerHttpRequest request) {
        Object serviceName = request.getHeaders().getFirst("Service-name");
        Object serviceUrl = request.getHeaders().getFirst("Service-url");
        if(serviceName != null && serviceUrl != null) {
            apiGateWayService.deleteApiUrl(serviceName.toString(), serviceUrl.toString());
        }
    }

    @GetMapping("/get-server")
    public Mono<ResponseEntity<CmnResponseVo>> api_getServerList(ServerHttpRequest request) throws IOException {
        return apiGateWayService.api_getServerList(request);
    }

}
