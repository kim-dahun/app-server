package com.service.discovery.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalRestControllerAdviceWebFlux {

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, String>>> exceptionHandler(Exception e, ServerHttpRequest httpRequest){

        log.error("System Error {}", e.getMessage());

        String message = System.lineSeparator() + " Request URI : " + httpRequest.getURI()
                + System.lineSeparator() + " Request Method : " + httpRequest.getMethod()
                + System.lineSeparator() + " System Message : " + e.getMessage();

        log.info(message);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("responseCode", "System Error");
        responseMap.put("responseMessage", message);

        HttpStatus httpStatus = e instanceof RuntimeException ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.BAD_REQUEST;
        return Mono.just(ResponseEntity.status(httpStatus).body(responseMap));
    }

}
