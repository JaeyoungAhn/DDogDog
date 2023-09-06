package com.babyblackdog.ddogdog.global.error;

import java.util.StringJoiner;
import org.springframework.http.HttpStatus;

public enum HotelErrorCode {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GL-000", "서버 에러입니다."),
  HOTEL_NOT_FOUND(HttpStatus.NOT_FOUND, "PL-100", "존재하지 않는 숙소입니다."),
  INVALID_BUSINESS_NAME(HttpStatus.BAD_REQUEST, "PL-200", "사업장 이름은 반드시 주어져야 합니다."),
  INVALID_HUMAN_NAME(HttpStatus.BAD_REQUEST, "PL-210", "사람 이름은 반드시 주어져야 합니다."),
  INVALID_PHONE_BLANK(HttpStatus.BAD_REQUEST, "PL-220", "전화번호는 반드시 주어져야 합니다."),
  INVALID_PHONE_LENGTH(HttpStatus.BAD_REQUEST, "PL-221", "전화번호는 11 자리여야 합니다."),
  INVALID_PHONE_DIGIT(HttpStatus.BAD_REQUEST, "PL-222", "전화번호는 모두 숫자여야 합니다."),
  INVALID_HOTEL_NAME(HttpStatus.BAD_REQUEST, "PL-230", "숙소 이름은 반드시 주어져야 합니다."),
  INVALID_PROVINCE_VALUE(HttpStatus.BAD_REQUEST, "PL-240", "지역 이름은 반드시 주어져야 합니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  HotelErrorCode(HttpStatus httpStatus, String code, String message) {
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
    return new StringJoiner(", ", HotelErrorCode.class.getSimpleName() + "[", "]")
        .add("httpStatus=" + httpStatus)
        .add("code='" + code + "'")
        .add("message='" + message + "'")
        .toString();
  }
}
