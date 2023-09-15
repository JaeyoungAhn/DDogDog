package com.babyblackdog.ddogdog.global.exception;

public class RatingException extends RuntimeException {

  private final ErrorCode errorCode;

  public RatingException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public RatingException(ErrorCode errorCode, Exception exception) {
    super(errorCode.toString(), exception);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

}
