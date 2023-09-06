package com.babyblackdog.ddogdog.place.room;

//숙소, 객실 이름과 객실의 가격
public record RoomSimpleResult(
    String hotelName,
    String roomName,
    String roomDescription,
    long point
) {

}
