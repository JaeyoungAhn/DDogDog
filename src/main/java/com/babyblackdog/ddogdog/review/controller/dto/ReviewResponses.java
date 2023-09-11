package com.babyblackdog.ddogdog.review.controller.dto;


import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;

import java.time.LocalDate;
import java.util.List;

public record ReviewResponse(List<ReviewResponse> reviewResponses) {

    public static ReviewResponse of(ReviewResult result) {
        return new ReviewResponse(
                result.id(),
                result.content(),
                result.rating(),
                result.userId(),
                result.createdDate()
        );
    }

}
