package com.babyblackdog.ddogdog.review.controller.dto;

public record ReviewRequest(Long orderId,
                            Long hotelId,
                            Long roomId,
                            String content,
                            Double rating) {

}
