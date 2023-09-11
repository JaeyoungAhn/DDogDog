package com.babyblackdog.ddogdog.review.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.review.application.ReviewFacade;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponse;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponses;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewRestController {

  private final ReviewFacade facade;

  public ReviewRestController(ReviewFacade facade) {
    this.facade = facade;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponse> createReview(@RequestParam Long roomId,
                                                     @RequestParam Long reservationId,
                                                     @RequestParam String content,
                                                     @RequestParam Double rating) {
    ReviewResult addedReview = facade.registerReview(roomId, reservationId, content, rating);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ReviewResponse.of(addedReview));
  }

  @PutMapping(value = "/{reviewId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponse> modifyReview(@PathVariable Long reviewId,
                                                     @RequestParam String content,
                                                     @RequestParam Double rating) {
    ReviewResult updatedReview = facade.updateReview(reviewId, content, rating);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ReviewResponse.of(updatedReview));
  }

  @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponses> getReviewsByUserId(@PathVariable Long userId) {
    List<ReviewResult> retrievedReviews = facade.findReviewsByUserId(userId);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ReviewResponse.of(retrievedReviews));
  }
}
