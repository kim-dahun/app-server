package com.service.core.service.impl;

import com.service.core.constants.MessageConstants;
import com.service.core.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

import static com.service.core.constants.MessageConstants.KO;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    private final LocaleResolver localeResolver;

    @Override
    public String getMessage(String langCode, Object[] msgParams ,String msgCode) {
        Locale locale = Locale.of(langCode);

        return messageSource.getMessage(msgCode,msgParams, locale);


    }

    @Override
    public String getMessage(Object[] msgParams, String msgCode) {
        Locale locale = Locale.KOREAN;
        return messageSource.getMessage(msgCode,msgParams, locale);
    }
}
