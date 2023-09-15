package com.babyblackdog.ddogdog.global.exception;

public class WishlistException extends RuntimeException {

    private final ErrorCode errorCode;

    public WishlistException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public WishlistException(ErrorCode errorCode, Exception exception) {
        super(errorCode.toString(), exception);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}