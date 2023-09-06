package com.babyblackdog.ddogdog.global.exception;

import com.babyblackdog.ddogdog.global.error.HotelErrorCode;
import java.util.StringJoiner;
import org.springframework.http.HttpStatus;

public class ErrorResponse {

  private final HotelErrorCode hotelErrorCode;

  protected ErrorResponse(HotelErrorCode hotelErrorCode) {
    this.hotelErrorCode = hotelErrorCode;
  }

  protected static ErrorResponse of(HotelErrorCode hotelErrorCode) {
    return new ErrorResponse(hotelErrorCode);
  }

  protected HttpStatus getStatusCode() {
    return hotelErrorCode.getHttpStatus();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ErrorResponse.class.getSimpleName() + "[", "]")
        .add("hotelErrorCode=" + hotelErrorCode)
        .toString();
  }
}
