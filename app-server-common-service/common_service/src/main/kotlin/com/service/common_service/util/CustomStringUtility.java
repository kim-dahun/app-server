package com.service.common_service.util;

public class CustomStringUtility {


    public static String getWildCardString(String paramWord){
        StringBuilder sb = getStringBuilder();
            if(!isNotNull(paramWord)) {
                return "%%";
            }
            paramWord = paramWord.trim();
            if (isNotWildCard(paramWord, 0)) {
                sb.append("%");
            }

            sb.append(paramWord);

            if (isNotWildCard(paramWord,paramWord.length()-1)) {
                sb.append('%');
            }
            return sb.toString();


    }


    public static StringBuilder getStringBuilder(){
        return new StringBuilder();
    }

    public static boolean isNotWildCard(String paramWord, int index){
        return paramWord.charAt(index) != '%';
    }

    public static boolean isNotNull(String paramWord){
        return paramWord != null;
    }


}
