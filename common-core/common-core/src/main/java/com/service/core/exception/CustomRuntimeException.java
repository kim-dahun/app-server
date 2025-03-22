package com.service.core.exception;

public class CustomRuntimeException extends RuntimeException {

    private String responseMessage;
    private Integer responseCode;

    private String customMessage;

    public CustomRuntimeException(){
        super();
    }

    public CustomRuntimeException(String responseMessage){
        super();
        this.responseMessage = responseMessage;
        this.responseCode = 500;
    }

    public CustomRuntimeException(String responseMessage, Integer responseCode){
        super();
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public CustomRuntimeException(String responseMessage, Integer responseCode, String customMessage){
        super(customMessage);
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public CustomRuntimeException(Integer responseCode){
        super();
        this.responseCode = responseCode;
        this.responseMessage = "FAIL";
    }

    public CustomRuntimeException(Integer responseCode, String customMessage){
        super(customMessage);
        this.responseCode = responseCode;
        this.responseMessage = "FAIL";
    }

    public CustomRuntimeException(String responseMessage, String customMessage){
        super(customMessage);
        this.responseMessage = responseMessage;
        this.responseCode = 500;
    }


}
