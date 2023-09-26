package com.babyblackdog.ddogdog.global.exception;

public class RoomException extends RuntimeException {

    private final ErrorCode errorCode;

    public RoomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public RoomException(ErrorCode errorCode, Exception exception) {
        super(errorCode.toString(), exception);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
