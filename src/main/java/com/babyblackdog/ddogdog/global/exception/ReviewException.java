package com.babyblackdog.ddogdog.global.exception;

import com.babyblackdog.ddogdog.global.error.ReviewErrorCode;

public class ReviewException extends RuntimeException {

  public ReviewException(ReviewErrorCode reviewErrorCode) {
    super(reviewErrorCode.toString());
  }

  public ReviewException(ReviewErrorCode reviewErrorCode, Exception exception) {
    super(reviewErrorCode.getMessage(), exception);
  }
}