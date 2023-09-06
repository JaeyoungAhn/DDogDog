package com.babyblackdog.ddogdog.global.exception;

import com.babyblackdog.ddogdog.global.error.HotelErrorCode;

public class HotelException extends RuntimeException {

  private final HotelErrorCode hotelErrorCode;

  public HotelException(HotelErrorCode hotelErrorCode) {
    super(hotelErrorCode.toString());
    this.hotelErrorCode = hotelErrorCode;
  }

  public HotelException(HotelErrorCode hotelErrorCode, Exception exception) {
    super(hotelErrorCode.getMessage(), exception);
    this.hotelErrorCode = hotelErrorCode;
  }

  public HotelErrorCode getErrorCode() {
    return hotelErrorCode;
  }
}
