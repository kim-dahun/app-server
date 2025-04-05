package com.service.discovery.api.service;

import com.service.core.vo.response.CmnResponseVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ApiGateWayService {



    ResponseEntity<CmnResponseVo> api_doHttpRequest(HttpServletRequest request);

    Boolean isAuth(HttpServletRequest request);

    String getApiUrl(String serviceName);

    void setApiUrl(String serviceName, String apiUrl);

    Boolean api_insertRequestHistory(HttpServletRequest request);

    Boolean isBadRequest(HttpServletRequest request);

}
