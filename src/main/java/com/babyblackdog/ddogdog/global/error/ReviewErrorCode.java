package com.babyblackdog.ddogdog.global.error;

import java.util.StringJoiner;
import org.springframework.http.HttpStatus;

public enum ReviewErrorCode {
  INVALID_RATING(HttpStatus.BAD_REQUEST, "R100", "유효하지 않은 별점입니다."),
  INVALID_DECIMAL_POINT(HttpStatus.BAD_REQUEST, "R200", "유효하지 않은 소숫점 자리입니다."),
  INVALID_REVIEW(HttpStatus.BAD_REQUEST, "R300", "유효하지 않은 리뷰입니다."),
  INVALID_CONTENT(HttpStatus.BAD_REQUEST, "R400", "유효하지 않은 리뷰 내용입니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  ReviewErrorCode(HttpStatus httpStatus, String code, String message) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ReviewErrorCode.class.getSimpleName() + "[", "]")
        .add("httpStatus=" + httpStatus)
        .add("code='" + code + "'")
        .add("message='" + message + "'")
        .toString();
  }
}