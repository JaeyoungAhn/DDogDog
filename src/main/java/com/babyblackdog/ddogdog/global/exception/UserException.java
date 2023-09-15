package com.babyblackdog.ddogdog.global.exception;

public class UserException extends RuntimeException {

  private final ErrorCode errorCode;

  public UserException(ErrorCode errorCode) {
    super(errorCode.toString());
    this.errorCode = errorCode;
  }

  public UserException(ErrorCode errorCode, Exception exception) {
    super(errorCode.toString(), exception);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
