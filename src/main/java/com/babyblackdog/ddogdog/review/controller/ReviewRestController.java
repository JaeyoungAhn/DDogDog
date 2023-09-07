package com.babyblackdog.ddogdog.review.controller;

import com.babyblackdog.ddogdog.review.application.ReviewFacade;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponse;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/reviews", produces = APPLICATION_JSON_VALUE)
public class ReviewRestController {

  private final ReviewFacade facade;

  public ReviewRestController(ReviewFacade facade) {
    this.facade = facade;
  }

  @PostMapping
  public ResponseEntity<ReviewResponse> createReview(
          @RequestParam Long roomId,
          @RequestParam Long reservationId,
          @RequestParam String content,
          @RequestParam Double rating) {

    ReviewResult addedReview = facade.registerReview(roomId, reservationId, content, rating);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ReviewResponse.of(addedReview));
  }

}
