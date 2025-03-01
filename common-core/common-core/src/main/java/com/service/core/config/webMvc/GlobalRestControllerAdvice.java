package com.service.core.config.webMvc;

import ch.qos.logback.core.joran.spi.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> exceptionHandler(Exception e, HttpServletRequest httpServletRequest){

        log.error("System Error {}", e.getMessage());

        String message = System.lineSeparator() + " Request URI : " + httpServletRequest.getRequestURI()
                + System.lineSeparator() + " Request Method : " + httpServletRequest.getMethod()
                + System.lineSeparator() + " System Message : " + e.getMessage();

        log.info(message);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("responseCode", "System Error");
        responseMap.put("responseMessage", message);

        HttpStatus httpStatus = e instanceof RuntimeException ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(responseMap);
    }


}
