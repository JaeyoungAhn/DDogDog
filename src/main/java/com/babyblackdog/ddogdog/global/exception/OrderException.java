package com.babyblackdog.ddogdog.global.exception;

public class OrderException extends RuntimeException {

    private final ErrorCode errorCode;

    public OrderException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public OrderException(ErrorCode errorCode, Exception exception) {
        super(errorCode.toString(), exception);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
