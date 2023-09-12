package com.babyblackdog.ddogdog.review.controller;

import com.babyblackdog.ddogdog.review.controller.dto.ReviewRequest;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponse;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponses;
import com.babyblackdog.ddogdog.review.service.ReviewService;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewRestController {
  private final ReviewService service;

  public ReviewRestController(ReviewService service) {
    this.service = service;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest) {
    ReviewResult addedReviewResult = service.registerReview(
            reviewRequest.roomId(),
            reviewRequest.content(),
            reviewRequest.rating(),
            reviewRequest.userId());

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ReviewResponse.of(addedReviewResult));
  }

  @PutMapping(value = "/{reviewId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponse> modifyReview(@PathVariable Long reviewId,
      @RequestParam String content,
      @RequestParam Double rating) {
    ReviewResult updatedReviewResult = service.updateReview(reviewId, content, rating);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ReviewResponse.of(updatedReviewResult));
  }

  @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponses> getReviewsByUserId(@PathVariable Long userId,
                                                            Pageable pageable) {
    ReviewResults retrievedReviewsResult = service.findReviewsByUserId(userId, pageable);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ReviewResponses.of(retrievedReviewsResult));
  }
}
