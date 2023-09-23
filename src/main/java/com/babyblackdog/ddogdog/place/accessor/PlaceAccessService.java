package com.babyblackdog.ddogdog.place.accessor;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.place.accessor.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.place.service.dto.HotelResult;
import java.util.List;

public interface PlaceAccessService {

    /**
     * 예약 페이지에 들어올 때, 해당 정보를 받아 예약 페이지를 구성해야 함
     *
     * @return 숙소, 객실 이름과 객실의 가격
     */
    RoomSimpleResult findRoomSimpleInfo(Long roomId);

    /**
     * 리뷰 추가 시 해당 리뷰 점수를 평균 별점에 갱신
     *
     * @param hotelId
     * @param ratingScore
     */
    void addRatingScoreOfHotel(Long hotelId, double ratingScore);

    /**
     * 리뷰 등록을 위해 호텔에 대한 객실 조회
     *
     * @param hotelId
     * @return
     */
    List<Long> findRoomIdsOfHotel(Long hotelId);

    /**
     * 리뷰 등록을 위해 호텔이 존재하는지 조회
     *
     * @param hotelId
     * @return boolean
     */
    boolean isHotelValid(Long hotelId);

    /**
     * 쿠폰 생성을 위해 점주가 가지고 있는 호텔 조회
     *
     * @param email
     * @return 숙소
     */
    HotelResult findHotelByEmail(Email email);
}
