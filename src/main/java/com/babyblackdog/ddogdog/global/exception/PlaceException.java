package com.babyblackdog.ddogdog.global.exception;

import com.babyblackdog.ddogdog.global.error.ErrorCode;

public class PlaceException extends RuntimeException {

  public PlaceException(ErrorCode errorCode) {
    super(errorCode.toString());
  }

  public PlaceException(ErrorCode errorCode, Exception exception) {
    super(errorCode.getMessage(), exception);
  }
}
