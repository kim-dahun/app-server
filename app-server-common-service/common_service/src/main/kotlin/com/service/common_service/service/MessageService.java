package com.service.common_service.service;

import jakarta.servlet.http.HttpServletRequest;

public interface MessageService {

    String getMessage(String langCode, Object[] msgParams , String msgCode);

    String getMessage(HttpServletRequest request, Object[] msgParams, String msgCode);
}
