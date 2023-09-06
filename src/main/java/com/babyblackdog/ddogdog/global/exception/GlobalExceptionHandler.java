package com.babyblackdog.ddogdog.global.exception;

import com.babyblackdog.ddogdog.global.error.HotelErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(HotelException.class)
  public ResponseEntity<ErrorResponse> placeExceptionHandler(HotelException exception) {
    ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode());
    logger.info("HotelException: {}", errorResponse);
    return ResponseEntity
        .status(errorResponse.getStatusCode())
        .body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> internalServerErrorExceptionHandler(Exception exception) {
    ErrorResponse errorResponse = ErrorResponse.of(HotelErrorCode.INTERNAL_SERVER_ERROR);
    logger.info("Exception: {}", errorResponse);
    return ResponseEntity
        .status(errorResponse.getStatusCode())
        .body(errorResponse);
  }

}
