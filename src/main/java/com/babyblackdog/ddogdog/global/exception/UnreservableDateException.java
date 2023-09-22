package com.babyblackdog.ddogdog.global.exception;

public class UnreservableDateException extends RuntimeException {

    private final ErrorCode errorCode;

    public UnreservableDateException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public UnreservableDateException(ErrorCode errorCode, Exception exception) {
        super(errorCode.toString(), exception);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
