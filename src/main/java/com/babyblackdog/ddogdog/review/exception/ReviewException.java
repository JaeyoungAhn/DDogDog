package com.babyblackdog.ddogdog.review.exception;

import com.babyblackdog.ddogdog.review.error.ReviewErrorCode;

public class ReviewException extends RuntimeException {

    public ReviewException(ReviewErrorCode reviewErrorCode) {
        super(reviewErrorCode.toString());
    }

    public ReviewException(ReviewErrorCode reviewErrorCode, Exception exception) {
        super(reviewErrorCode.getMessage(), exception);
    }
}