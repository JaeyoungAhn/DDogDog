package com.babyblackdog.ddogdog.review.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.review.application.ReviewFacade;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewModifyRequest;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewRequest;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponse;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewResponses;
import com.babyblackdog.ddogdog.review.domain.vo.RatingScore;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewRestController {

    private final ReviewFacade facade;
    private final JwtSimpleAuthentication authentication;

    public ReviewRestController(ReviewFacade facade, JwtSimpleAuthentication authentication) {
        this.facade = facade;
        this.authentication = authentication;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest) {

        Email email = authentication.getEmail();

        ReviewResult addedReviewResult = facade.registerReview(
                reviewRequest.orderId(),
                reviewRequest.hotelId(),
                reviewRequest.roomId(),
                reviewRequest.content(),
                new RatingScore(reviewRequest.rating()),
                email.getValue());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ReviewResponse.of(addedReviewResult));
    }

    @PatchMapping(value = "/{reviewId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponse> modifyReview(@PathVariable Long reviewId,
            @RequestBody ReviewModifyRequest reviewModifyRequest) {
        ReviewResult updatedReviewResult = facade.updateReview(reviewId, reviewModifyRequest.content());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ReviewResponse.of(updatedReviewResult));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponses> getReviewsByHotelId(@RequestParam("hotelId") Long hotelId,
            Pageable pageable) {

        ReviewResults retrievedReviewsResult = facade.findReviewsByHotelId(hotelId, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ReviewResponses.of(retrievedReviewsResult));

    }

    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponses> getReviewsForMe(Pageable pageable) {
        Email email = authentication.getEmail();

        ReviewResults retrievedReviewsResult = facade.findReviewsByEmail(email.getValue(), pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ReviewResponses.of(retrievedReviewsResult));
    }
}
