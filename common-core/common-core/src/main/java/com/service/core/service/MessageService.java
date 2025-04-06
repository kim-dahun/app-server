package com.service.core.service;

import jakarta.servlet.http.HttpServletRequest;

public interface MessageService {

    String getMessage(String langCode, Object[] msgParams , String msgCode);

    String getMessage( Object[] msgParams, String msgCode);
}
