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

    // todo: JWT 내 이메일 가져오기
    String email = "test@ddog.dog";

    ReviewResult addedReviewResult = facade.registerReview(
        reviewRequest.roomId(),
        reviewRequest.content(),
        reviewRequest.rating(),
        email);

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

  @GetMapping(value = "/{hotelId}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponses> getReviewsByHotelId(@PathVariable Long hotelId,
      Pageable pageable) {

    // Todo: JWT내 권한이 ADMIN 인지 확인

    ReviewResults retrievedReviewsResult = facade.findReviewsByHotelId(hotelId, pageable);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(ReviewResponses.of(retrievedReviewsResult));

  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewResponses> getReviewsForMe(Pageable pageable) {

    // todo: JWT 내에서 이메일 가져오기
    String email = "test@ddog.dog";

    ReviewResults retrievedReviewsResult = facade.findReviewsByEmail(email, pageable);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(ReviewResponses.of(retrievedReviewsResult));
  }
}
