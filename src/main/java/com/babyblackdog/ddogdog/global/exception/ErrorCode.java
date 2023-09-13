package com.babyblackdog.ddogdog.global.exception;

import org.springframework.http.HttpStatus;

import java.util.StringJoiner;

public enum ErrorCode {

  // global
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GL-000", "서버 에러입니다."),

  // hotel
  HOTEL_NOT_FOUND(HttpStatus.NOT_FOUND, "PL-100", "존재하지 않는 숙소입니다."),
  INVALID_BUSINESS_NAME(HttpStatus.BAD_REQUEST, "PL-200", "사업장 이름은 반드시 주어져야 합니다."),
  INVALID_HUMAN_NAME(HttpStatus.BAD_REQUEST, "PL-210", "사람 이름은 반드시 주어져야 합니다."),
  INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "PL-220", "올바르지 않은 전화번호 형식입니다."),
  INVALID_HOTEL_NAME(HttpStatus.BAD_REQUEST, "PL-230", "숙소 이름은 반드시 주어져야 합니다."),
  INVALID_PROVINCE_VALUE(HttpStatus.BAD_REQUEST, "PL-240", "지역 이름은 반드시 주어져야 합니다."),

  // Review
  REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "R-100", "존재하지 않는 리뷰입니다."),
  INVALID_RATING(HttpStatus.BAD_REQUEST, "R-200", "유효하지 않은 별점입니다."),
  INVALID_DECIMAL_POINT(HttpStatus.BAD_REQUEST, "R-210", "유효하지 않은 소숫점 자리입니다."),
  INVALID_REVIEW(HttpStatus.BAD_REQUEST, "R-220", "유효하지 않은 리뷰입니다."),

  INVALID_REVIEW_LENGTH(HttpStatus.BAD_REQUEST, "R-230", "리뷰 길이는 최소 10글자 이상이어야 합니다."),
  INVALID_RATING_RANGE(HttpStatus.BAD_REQUEST, "R-240", "유효하지 않은 리뷰 별점입니다."),
  INVALID_REVIEW_PARAMETER(HttpStatus.BAD_REQUEST, "R-250", "유효하지 않은 파라미터입니다."),
  INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "R-260", "유효하지 않은 이메일입니다."),
  EMPTY_REVIEW_CONTENT(HttpStatus.BAD_REQUEST, "R-300", "리뷰 내용이 비어있습니다."),
  EMPTY_REVIEW_EMAIL(HttpStatus.BAD_REQUEST, "R-310", "이메일란이 비어있습니다."),

  // room
  ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "RM-100", "존재하지 않는 객실입니다."),
  INVALID_OCCUPANCY_LOWER(HttpStatus.BAD_REQUEST, "RM-200", "최대 수용 인원은 양수여야 합니다."),
  INVALID_OCCUPANCY_UPPER(HttpStatus.BAD_REQUEST, "RM-210", "최대 수용 인원은 10명까지 입니다."),
  INVALID_ROOM_NUMBER(HttpStatus.BAD_REQUEST, "RM-220", "방 번호는 반드시 주어져야 합니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus httpStatus, String code, String message) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ErrorCode.class.getSimpleName() + "[", "]")
        .add("httpStatus=" + httpStatus)
        .add("code='" + code + "'")
        .add("message='" + message + "'")
        .toString();
  }
}
