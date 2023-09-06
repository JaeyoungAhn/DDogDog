package com.babyblackdog.ddogdog.place;

import com.babyblackdog.ddogdog.common.Point;

//숙소, 객실 이름과 객실의 가격
public record RoomBookingResult(String placeName, String roomName, Point point) {
}
