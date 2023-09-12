package com.babyblackdog.ddogdog.review.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.review.application.ReviewFacade;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponse;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponses;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewRestController {

  private final ReviewFacade facade;

  public ReviewRestController(ReviewFacade facade) {
    this.facade = facade;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponse> createReview(@RequestParam Long roomId,
      @RequestParam String content,
      @RequestParam Double rating,
      @RequestParam Long userId) {
    ReviewResult addedReviewResult = facade.registerReview(roomId, content, rating, userId);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ReviewResponse.of(addedReviewResult));
  }

  @PutMapping(value = "/{reviewId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponse> modifyReview(@PathVariable Long reviewId,
      @RequestParam String content,
      @RequestParam Double rating) {
    ReviewResult updatedReviewResult = facade.updateReview(reviewId, content, rating);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ReviewResponse.of(updatedReviewResult));
  }

  @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponses> getReviewsByUserId(@PathVariable Long userId,
      Pageable pageable) {
    ReviewResults retrievedReviewsResult = facade.findReviewsByUserId(userId, pageable);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ReviewResponses.of(retrievedReviewsResult));
  }
}
