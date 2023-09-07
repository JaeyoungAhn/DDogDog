package com.babyblackdog.ddogdog.global.exception;

import com.babyblackdog.ddogdog.global.error.ReviewRoomReservationErrorCode;

public class ReviewRoomReservationException extends RuntimeException {

    public ReviewRoomReservationException(ReviewRoomReservationErrorCode reviewRoomReservationErrorCode) {
        super(reviewRoomReservationErrorCode.toString());
    }

    public ReviewRoomReservationException(ReviewRoomReservationErrorCode reviewRoomReservationErrorCode, Exception exception) {
        super(reviewRoomReservationErrorCode.getMessage(), exception);
    }
}