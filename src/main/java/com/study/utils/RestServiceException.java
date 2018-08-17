package com.study.utils;

public class RestServiceException extends Exception {

    private static final long serialVersionUID = 454595045612592188L;

    private ServiceErrorCode errorCode;

    public RestServiceException() {
        super();
    }

    public RestServiceException(ServiceErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public RestServiceException(String message, ServiceErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public RestServiceException(String message, Throwable cause, ServiceErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public RestServiceException(Throwable cause, ServiceErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ServiceErrorCode getErrorCode() {
        return errorCode;
    }

}
