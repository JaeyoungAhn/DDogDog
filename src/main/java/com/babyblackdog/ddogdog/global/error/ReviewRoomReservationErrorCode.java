package com.babyblackdog.ddogdog.global.error;

import org.springframework.http.HttpStatus;

import java.util.StringJoiner;

public enum ReviewRoomReservationErrorCode {
  INVALID_ROOM_ID(HttpStatus.BAD_REQUEST, "RRR100", "유효하지 않은 객실번호입니다."),
  INVALID_RESERVATION_ID(HttpStatus.BAD_REQUEST, "RRR200", "유효하지 않은 예약번호입니다."),
  INVALID_REVIEW_ID(HttpStatus.BAD_REQUEST, "RRR300", "유효하지 않은 리뷰번호입니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  ReviewRoomReservationErrorCode(HttpStatus httpStatus, String code, String message) {
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
    return new StringJoiner(", ", ReviewRoomReservationErrorCode.class.getSimpleName() + "[", "]")
        .add("httpStatus=" + httpStatus)
        .add("code='" + code + "'")
        .add("message='" + message + "'")
        .toString();
  }
}