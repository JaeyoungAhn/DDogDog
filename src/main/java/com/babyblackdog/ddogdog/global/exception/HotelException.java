package com.babyblackdog.ddogdog.global.exception;

public class HotelException extends RuntimeException {

  private final ErrorCode errorCode;

  public HotelException(ErrorCode errorCode) {
    super(errorCode.toString());
    this.errorCode = errorCode;
  }

  public HotelException(ErrorCode errorCode, Exception exception) {
    super(errorCode.toString(), exception);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
