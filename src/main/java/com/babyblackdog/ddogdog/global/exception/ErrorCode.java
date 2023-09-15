package com.babyblackdog.ddogdog.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.StringJoiner;
import org.springframework.http.HttpStatus;

public enum ErrorCode {

  // global
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GL-000", "서버 에러입니다."),

  // hotel
  HOTEL_NOT_FOUND(NOT_FOUND, "PL-100", "존재하지 않는 숙소입니다."),
  INVALID_BUSINESS_NAME(BAD_REQUEST, "PL-200", "사업장 이름은 반드시 주어져야 합니다."),
  INVALID_HUMAN_NAME(BAD_REQUEST, "PL-210", "사람 이름은 반드시 주어져야 합니다."),
  INVALID_PHONE_NUMBER(BAD_REQUEST, "PL-220", "올바르지 않은 전화번호 형식입니다."),
  INVALID_HOTEL_NAME(BAD_REQUEST, "PL-230", "숙소 이름은 반드시 주어져야 합니다."),
  INVALID_PROVINCE_VALUE(BAD_REQUEST, "PL-240", "지역 이름은 반드시 주어져야 합니다."),

  // Review
  INVALID_RATING(BAD_REQUEST, "R100", "유효하지 않은 별점입니다."),
  INVALID_DECIMAL_POINT(BAD_REQUEST, "R200", "유효하지 않은 소숫점 자리입니다."),
  INVALID_REVIEW(BAD_REQUEST, "R300", "유효하지 않은 리뷰입니다."),
  EMPTY_REVIEW_CONTENT(BAD_REQUEST, "R400", "유효하지 않은 리뷰 내용입니다."),
  INVALID_REVIEW_LENGTH(BAD_REQUEST, "R500", "리뷰 길이는 최소 10글자 이상이어야 합니다."),
  INVALID_RATING_RANGE(BAD_REQUEST, "R600", "유효하지 않은 리뷰 별점입니다."),

  // room
  ROOM_NOT_FOUND(NOT_FOUND, "RM-100", "존재하지 않는 객실입니다."),
  INVALID_OCCUPANCY_LOWER(BAD_REQUEST, "RM-200", "최대 수용 인원은 양수여야 합니다."),
  INVALID_OCCUPANCY_UPPER(BAD_REQUEST, "RM-210", "최대 수용 인원은 10명까지 입니다."),
  INVALID_ROOM_NUMBER(BAD_REQUEST, "RM-220", "방 번호는 반드시 주어져야 합니다."),
  INVALID_ROOM_TYPE(BAD_REQUEST, "RM-230", "유효하지 않은 방 타입입니다."),

  // reservation
  INVALID_ROOM_ID(BAD_REQUEST, "RRR100", "유효하지 않은 객실번호입니다."),
  INVALID_RESERVATION_ID(BAD_REQUEST, "RRR200", "유효하지 않은 예약번호입니다."),
  INVALID_REVIEW_ID(BAD_REQUEST, "RRR300", "유효하지 않은 리뷰번호입니다."),

  // user
  USER_NOT_FOUND(BAD_REQUEST, "US-100", "존재하지 않는 유저입니다."),
  INVALID_ROLE(BAD_REQUEST, "US-110", "유효하지 않은 권한입니다.");

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

  public String getCode() {
    return code;
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
