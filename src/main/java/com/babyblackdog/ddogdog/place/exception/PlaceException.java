package com.babyblackdog.ddogdog.place.exception;

public class PlaceException extends RuntimeException {

  private final ErrorCode errorCode;

  public PlaceException(ErrorCode errorCode) {
    super(errorCode.toString());
    this.errorCode = errorCode;
  }

  public PlaceException(ErrorCode errorCode, Exception exception) {
    super(errorCode.getMessage(), exception);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
