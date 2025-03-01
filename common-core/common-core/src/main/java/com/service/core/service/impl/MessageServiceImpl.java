package com.service.core.service.impl;

import com.service.core.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    private final LocaleResolver localeResolver;

    @Override
    public String getMessage(String langCode, Object[] msgParams ,String msgCode) {
        Locale locale = new Locale(langCode);
        return messageSource.getMessage(msgCode,msgParams, locale);
    }

    @Override
    public String getMessage(HttpServletRequest request, Object[] msgParams, String msgCode) {
        Locale locale = localeResolver.resolveLocale(request);
        return messageSource.getMessage(msgCode,msgParams, locale);
    }
}
