package com.babyblackdog.ddogdog.global.exception;

import java.util.StringJoiner;

public class ErrorResponse {

  private final String message;

  protected ErrorResponse(ErrorCode errorCode) {
    this.message = "[%s] - %s".formatted(errorCode.getCode(), errorCode.getMessage());
  }

  protected static ErrorResponse of(ErrorCode errorCode) {
    return new ErrorResponse(errorCode);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ErrorResponse.class.getSimpleName() + "[", "]")
        .add("message='" + message + "'")
        .toString();
  }
}
