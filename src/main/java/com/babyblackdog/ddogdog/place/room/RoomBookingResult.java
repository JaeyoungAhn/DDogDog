package com.babyblackdog.ddogdog.place.room;

import com.babyblackdog.ddogdog.common.Point;

//숙소, 객실 이름과 객실의 가격
public record RoomBookingResult(String placeName, String roomName, long point) {

}
