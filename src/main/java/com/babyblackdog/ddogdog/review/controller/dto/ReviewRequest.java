package com.babyblackdog.ddogdog.review.controller.dto;

public record ReviewRequest(Long roomId,
                            String content,
                            Double rating,
                            Long userId) {
}
