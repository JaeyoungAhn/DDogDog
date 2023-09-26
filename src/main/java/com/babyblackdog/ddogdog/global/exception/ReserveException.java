package com.babyblackdog.ddogdog.global.exception;

public class ReserveException extends RuntimeException {

    private final ErrorCode errorCode;

    public ReserveException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public ReserveException(ErrorCode errorCode, Exception exception) {
        super(errorCode.toString(), exception);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
