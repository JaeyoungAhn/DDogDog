package com.babyblackdog.ddogdog.global.exception;

public class ReviewException extends RuntimeException {

    private final ErrorCode errorCode;

    public ReviewException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ReviewException(ErrorCode errorCode, Exception exception) {
        super(errorCode.toString(), exception);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}