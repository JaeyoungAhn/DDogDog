package com.babyblackdog.ddogdog.global.exception;

public class ReviewRoomReservationException extends RuntimeException {

    public ReviewRoomReservationException(ErrorCode reviewRoomReservationErrorCode) {
        super(reviewRoomReservationErrorCode.toString());
    }

    public ReviewRoomReservationException(
            ErrorCode reviewRoomReservationErrorCode, Exception exception) {
        super(reviewRoomReservationErrorCode.getMessage(), exception);
    }
}