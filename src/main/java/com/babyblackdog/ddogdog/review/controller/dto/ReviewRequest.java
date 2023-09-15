package com.babyblackdog.ddogdog.review.controller.dto;

public record ReviewRequest(Long orderId,
                            Long roomId,
                            String content,
                            Double rating) {

}
