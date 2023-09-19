package com.babyblackdog.ddogdog.place;

import com.babyblackdog.ddogdog.place.controller.dto.AddHotelRequest;
import com.babyblackdog.ddogdog.place.controller.dto.AddRoomRequest;
import com.babyblackdog.ddogdog.place.model.Hotel;
import com.babyblackdog.ddogdog.place.model.Rating;
import com.babyblackdog.ddogdog.place.model.Room;
import com.babyblackdog.ddogdog.place.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.service.dto.AddRoomParam;
import org.springframework.stereotype.Component;

@Component
public class PlaceTestData {

    public AddHotelRequest addHotelRequest() {
        return new AddHotelRequest(
                "신라호텔",
                "서울",
                1L,
                "010-1234-4321",
                "이부진",
                "신세계"
        );
    }

    public AddHotelParam addHotelParam() {
        return AddHotelRequest.to(addHotelRequest());
    }

    public Hotel hotelEntity() {
        return AddHotelParam.to(addHotelParam());
    }

    private AddRoomRequest addRoomRequest() {
        return new AddRoomRequest(
                "더블",
                "침대가 두 개지요",
                4,
                true,
                false,
                true,
                "404",
                50_000
        );
    }

    public AddRoomParam addRoomParam(Long hotelId) {
        return AddRoomRequest.to(hotelId, addRoomRequest());
    }

    public Room roomEntity(Hotel hotel) {
        return AddRoomParam.to(hotel, addRoomParam(hotel.getId()));
    }

    private Rating ratingEntity() {
        return new Rating(0, 0);
    }

}
