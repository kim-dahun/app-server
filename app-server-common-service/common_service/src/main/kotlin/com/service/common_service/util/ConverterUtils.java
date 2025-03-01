package com.service.common_service.util;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

public class ConverterUtils {

    private static final String REGEX = "[^0-9]";

    public static String getTimeKeySecond(LocalDateTime now){
        return now.toString().replace(REGEX,"").substring(0,14);
    }

    public static String getTimeKeyMillSecond(LocalDateTime now){
        return now.toString().replace(REGEX,"").substring(0,17);
    }

    public static String getTimeKeyDay(LocalDateTime now){
        return now.toString().replace(REGEX,"").substring(0,8);
    }

    public static String getTimeKeyMinute(LocalDateTime now){
        return now.toString().replace(REGEX,"").substring(0,8);
    }

    public static String getBase64EncdoingString(byte[] imgBytes){
        return Base64.getEncoder().encodeToString(imgBytes);
    }

    public static byte[] getByteArray(String serializedString){
        return serializedString.getBytes(StandardCharsets.UTF_8);
    }

}
