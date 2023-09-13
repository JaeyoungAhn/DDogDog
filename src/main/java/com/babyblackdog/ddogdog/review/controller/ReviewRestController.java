package com.babyblackdog.ddogdog.review.controller;

import com.babyblackdog.ddogdog.global.exception.ReviewException;
import com.babyblackdog.ddogdog.review.application.ReviewFacade;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewRequest;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponse;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponses;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_REVIEW_PARAMETER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewRestController {
  private final ReviewFacade facade;

  public ReviewRestController(ReviewFacade facade) {
    this.facade = facade;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest) {
    ReviewResult addedReviewResult = facade.registerReview(
            reviewRequest.roomId(),
            reviewRequest.content(),
            reviewRequest.rating(),
            reviewRequest.userId());

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ReviewResponse.of(addedReviewResult));
  }

  @PatchMapping(value = "/{reviewId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponse> modifyReview(@PathVariable Long reviewId,
                                                     @RequestParam String content) {
    ReviewResult updatedReviewResult = facade.updateReview(reviewId, content);
    return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(ReviewResponse.of(updatedReviewResult));
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponses> getReviewsByUserId(@RequestParam(required = false) Long userId,
                                                            @RequestParam(required = false) Long hotelId,
                                                            Pageable pageable) {
    if (userId != null && hotelId == null) {
      ReviewResults retrievedReviewsResult = facade.findReviewsByUserId(userId, pageable);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(ReviewResponses.of(retrievedReviewsResult));
    }

    if (hotelId != null && userId == null) {
      ReviewResults retrievedReviewsResult = facade.findReviewsByHotelId(hotelId, pageable);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(ReviewResponses.of(retrievedReviewsResult));
    }

    throw new ReviewException(INVALID_REVIEW_PARAMETER);
  }
}
