package com.babyblackdog.ddogdog.global.exception;

public class UnorderableStateException extends RuntimeException {

    private final ErrorCode errorCode;

    public UnorderableStateException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public UnorderableStateException(ErrorCode errorCode, Exception exception) {
        super(errorCode.toString(), exception);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
