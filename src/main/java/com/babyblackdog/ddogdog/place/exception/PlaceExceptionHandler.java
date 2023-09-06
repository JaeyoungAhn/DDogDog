package com.babyblackdog.ddogdog.place.exception;

import static com.babyblackdog.ddogdog.place.exception.ErrorCode.INTERNAL_SERVER_ERROR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlaceExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(PlaceException.class)
  public ResponseEntity<ErrorResponse> placeExceptionHandler(PlaceException exception) {
    ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode());
    logger.info("PlaceException: {}", errorResponse);
    return ResponseEntity
        .status(errorResponse.getStatusCode())
        .body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> internalServerErrorExceptionHandler(Exception exception) {
    ErrorResponse errorResponse = ErrorResponse.of(INTERNAL_SERVER_ERROR);
    logger.info("Exception: {}", errorResponse);
    return ResponseEntity
        .status(errorResponse.getStatusCode())
        .body(errorResponse);
  }

}
