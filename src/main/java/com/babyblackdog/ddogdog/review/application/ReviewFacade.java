package com.babyblackdog.ddogdog.review.application;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.STAY_NOT_OVER;

import com.babyblackdog.ddogdog.global.exception.ReviewException;
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

    public ReviewResult registerReview(Long orderId, Long hotelId, Long roomId, String content, RatingScore rating,
            String email) {
        if (!orderReaderService.isStayOver(orderId)) {
            throw new ReviewException(STAY_NOT_OVER);
        }

        ReviewResult savedReviewResult = service.registerReview(orderId, roomId, content, rating.getValue(), email);

        placeAccessService.addRatingScoreOfHotel(hotelId, rating.getValue());

        return savedReviewResult;
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
