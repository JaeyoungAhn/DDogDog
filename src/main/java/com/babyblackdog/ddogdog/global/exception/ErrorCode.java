package com.babyblackdog.ddogdog.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
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

    // review
    REVIEW_NOT_FOUND(NOT_FOUND, "R-100", "존재하지 않는 리뷰입니다."),
    INVALID_RATING(BAD_REQUEST, "R-200", "유효하지 않은 별점입니다."),
    INVALID_DECIMAL_POINT(BAD_REQUEST, "R-210", "유효하지 않은 소숫점 자리입니다."),
    INVALID_REVIEW(BAD_REQUEST, "R-220", "유효하지 않은 리뷰입니다."),
    INVALID_REVIEW_LENGTH(BAD_REQUEST, "R-230", "리뷰 길이는 최소 10글자 이상이어야 합니다."),
    INVALID_RATING_RANGE(BAD_REQUEST, "R-240", "유효하지 않은 리뷰 별점입니다."),
    INVALID_REVIEW_PARAMETER(BAD_REQUEST, "R-250", "유효하지 않은 파라미터입니다."),
    EMPTY_REVIEW_CONTENT(BAD_REQUEST, "R-300", "리뷰 내용이 비어있습니다."),
    INVALID_REVIEW_PERMISSION(FORBIDDEN, "R-400", "리뷰 접근 권한이 없습니다."),
    STAY_NOT_OVER(BAD_REQUEST, "R-410", "숙박이 끝나지 않아 리뷰를 달 수 없습니다."),


    // room
    ROOM_NOT_FOUND(NOT_FOUND, "RM-100", "존재하지 않는 객실입니다."),
    INVALID_OCCUPANCY_LOWER(BAD_REQUEST, "RM-200", "최대 수용 인원은 양수여야 합니다."),
    INVALID_OCCUPANCY_UPPER(BAD_REQUEST, "RM-210", "최대 수용 인원은 10명까지 입니다."),
    INVALID_ROOM_NUMBER(BAD_REQUEST, "RM-220", "방 번호는 반드시 주어져야 합니다."),
    INVALID_ROOM_TYPE(BAD_REQUEST, "RM-230", "유효하지 않은 방 타입입니다."),

    // user
    USER_NOT_FOUND(BAD_REQUEST, "US-100", "존재하지 않는 유저입니다."),
    INVALID_ROLE(BAD_REQUEST, "US-110", "유효하지 않은 권한입니다."),
    FORBIDDEN_ROLE(FORBIDDEN, "US-120", "접근 권한이 부족합니다."),
    EMPTY_EMAIL(BAD_REQUEST, "US-130", "이메일란이 비어있습니다."),
    INVALID_EMAIL_FORMAT(BAD_REQUEST, "US-140", "유효하지 않은 이메일입니다"),
    EMAIL_NOT_FOUND(NOT_FOUND, "US-150", "존재하지 않는 이메일입니다."),

    // rating
    INVALID_RATING_SCORE(BAD_REQUEST, "RT-100", "평균별점은 0 이상이어야 합니다."),
    INVALID_RATING_COUNT(BAD_REQUEST, "RT-110", "별점 수는 0 이상이어야 합니다."),

    // wishlist
    WISHLIST_HOTEL_NOT_FOUND(FORBIDDEN, "W-200", "존재하지 않는 호텔입니다. "),
    WISHLIST_ALREADY_EXIST(BAD_REQUEST, "W-310", "이미 등록된 찜입니다."),
    WISHLIST_NOT_FOUND(NOT_FOUND, "W-110", "존재하지 않는 찜입니다."),
    INVALID_WISHLIST_PERMISSION(FORBIDDEN, "W-300", "찜에 대한 권한이 없습니다."),

    // point
    INVALID_POINT(BAD_REQUEST, "P-100", "포인트 값은 양수여야 합니다."),

    // coupon
    INVALID_COUPON_STATUS(BAD_REQUEST, "C-200", "이미 사용된 쿠폰입니다."),
    INVALID_INSTANT_COUPON_DATE(BAD_REQUEST, "C-210", "이미 만료된 즉시 할인 쿠폰입니다."),
    COUPON_PERMISSION_DENIED(FORBIDDEN, "C-300", "쿠폰 생성에 대한 권한이 없습니다.");



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
