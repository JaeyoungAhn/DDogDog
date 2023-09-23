package com.babyblackdog.ddogdog.global.exception;

public class CouponException extends RuntimeException {

    private final ErrorCode errorCode;

    public CouponException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public CouponException(ErrorCode errorCode, Exception exception) {
        super(errorCode.toString(), exception);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
