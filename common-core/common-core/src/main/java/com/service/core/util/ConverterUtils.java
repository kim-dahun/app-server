package com.service.core.util;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        return now.toString().replace(REGEX,"").substring(0,12);
    }

    public static String getDateTimeStringByFormat(LocalDateTime dateTime, String formatType){
        if(isValidPattern(formatType)){
            return dateTime.toString();
        }
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(formatType);
        return dateFormat.format(dateTime);

    }

    public static String getDateTimeStringByFormat(Timestamp dateTime, String formatType){
        if(isValidPattern(formatType)){
            return dateTime.toString();
        }
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(formatType);
        return dateFormat.format(dateTime.toLocalDateTime());
    }

    public static boolean isValidPattern(String pattern) {
        try {
            DateTimeFormatter.ofPattern(pattern);
            return false;
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return true;
        }
    }


    public static String getBase64EncdoingString(byte[] imgBytes){
        return Base64.getEncoder().encodeToString(imgBytes);
    }

    public static byte[] getByteArray(String serializedString){
        return serializedString.getBytes(StandardCharsets.UTF_8);
    }

}
