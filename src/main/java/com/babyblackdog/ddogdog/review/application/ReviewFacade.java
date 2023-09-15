package com.babyblackdog.ddogdog.review.application;

import com.babyblackdog.ddogdog.order.service.OrderReaderService;
import com.babyblackdog.ddogdog.place.accessor.PlaceAccessService;
import com.babyblackdog.ddogdog.review.domain.vo.RatingScore;
import com.babyblackdog.ddogdog.review.service.ReviewService;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResult;
import com.babyblackdog.ddogdog.review.service.dto.ReviewResults;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewFacade {

    private final ReviewService service;
    private final OrderReaderService orderReaderService;
    private final PlaceAccessService placeAccessService;

    public ReviewFacade(ReviewService service, OrderReaderService orderReaderService,
            PlaceAccessService placeAccessService) {
        this.service = service;
        this.orderReaderService = orderReaderService;
        this.placeAccessService = placeAccessService;
    }

    public ReviewResult registerReview(Long orderId, Long roomId, String content, RatingScore rating, String email) {

        placeAccessService.addRatingScoreOfHotel(roomId, rating.getValue());
        orderReaderService.isStayOver(orderId);
        return service.registerReview(orderId, roomId, content, rating.getValue(), email);
    }

    public ReviewResult updateReview(Long reviewId, String content) {
        return service.updateReview(reviewId, content);
    }

    public ReviewResults findReviewsByEmail(String email, Pageable pageable) {
        return service.findReviewsByEmail(email, pageable);
    }

    public ReviewResults findReviewsByHotelId(Long hotelId, Pageable pageable) {
        List<Long> roomIds = placeAccessService.findRoomIdsOfHotel(hotelId);
        return service.findReviewsByRoomIds(roomIds, pageable);
    }
}
