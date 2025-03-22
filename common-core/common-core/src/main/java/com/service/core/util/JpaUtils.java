package com.service.core.util;


import org.springframework.data.domain.ExampleMatcher;

public class JpaUtils {

    public static ExampleMatcher likeCondition(String fieldName, ExampleMatcher originalMatcher) {
        return originalMatcher.withMatcher(fieldName, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    }

    public static ExampleMatcher equalCondition(String fieldName, ExampleMatcher originalMatcher) {
        return originalMatcher.withMatcher(fieldName, ExampleMatcher.GenericPropertyMatchers.exact());
    }

    public static ExampleMatcher nullValueIgnore(ExampleMatcher originalMatcher) {
        return originalMatcher.withIgnoreNullValues();
    }
}
