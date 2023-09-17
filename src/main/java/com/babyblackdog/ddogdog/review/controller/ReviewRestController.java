package com.babyblackdog.ddogdog.review.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.review.application.ReviewFacade;
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

    public ReviewRestController(ReviewFacade facade) {
        this.facade = facade;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest) {

        JwtSimpleAuthentication jwt = JwtSimpleAuthentication.getInstance();
        Email email = jwt.getEmail();

        ReviewResult addedReviewResult = facade.registerReview(
                reviewRequest.orderId(),
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
            @RequestParam String content) {
        ReviewResult updatedReviewResult = facade.updateReview(reviewId, content);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ReviewResponse.of(updatedReviewResult));
    }

    @GetMapping(value = "/{hotelId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponses> getReviewsByHotelId(@PathVariable Long hotelId,
            Pageable pageable) {

        ReviewResults retrievedReviewsResult = facade.findReviewsByHotelId(hotelId, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ReviewResponses.of(retrievedReviewsResult));

    }

    @GetMapping(value = "/me", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponses> getReviewsForMe(Pageable pageable) {

        JwtSimpleAuthentication jwt = JwtSimpleAuthentication.getInstance();
        Email email = jwt.getEmail();

        ReviewResults retrievedReviewsResult = facade.findReviewsByEmail(email.getValue(), pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ReviewResponses.of(retrievedReviewsResult));
    }
}
