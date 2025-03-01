package com.service.core.exception;

public class CustomDocsParserException extends RuntimeException{

    // 기본 생성자
    public CustomDocsParserException() {
        super();
    }

    // 메시지를 받는 생성자
    public CustomDocsParserException(String message) {
        super(message);
    }

    // 메시지와 원인을 받는 생성자
    public CustomDocsParserException(String message, Throwable cause) {
        super(message, cause);
    }

    // 원인만 받는 생성자
    public CustomDocsParserException(Throwable cause) {
        super(cause);
    }

    // 모든 속성을 받는 생성자
    protected CustomDocsParserException(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
