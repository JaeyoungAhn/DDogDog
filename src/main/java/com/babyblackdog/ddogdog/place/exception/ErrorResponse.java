package com.babyblackdog.ddogdog.place.exception;

import java.util.StringJoiner;
import org.springframework.http.HttpStatus;

public class ErrorResponse {

  private final ErrorCode errorCode;

  protected ErrorResponse(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  protected static ErrorResponse of(ErrorCode errorCode) {
    return new ErrorResponse(errorCode);
  }

  protected HttpStatus getStatusCode() {
    return errorCode.getHttpStatus();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ErrorResponse.class.getSimpleName() + "[", "]")
        .add("errorCode=" + errorCode)
        .toString();
  }
}
