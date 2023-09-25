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

    public String getJwtToken() {
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJiYWJ5LWJsYWNrZG9nIiwiaWF0IjoxNjk1NjE2MTkzLCJleHAiOjE3MjcxNTIxOTMsImF1ZCI6IiIsInN1YiI6IiIsImVtYWlsIjoiMmJqQHNpbmxhLmNvbSIsInJvbGUiOiJPV05FUiJ9.0R3k2AoHSnDPZII4yZkto_qoispGto2FYlEx_xaWz55l-1EeC_e7DijrxnDqpoJOBNJCB_KlHfBh8IfB3m05rg";
    }

    public AddHotelRequest addHotelRequest() {
        return new AddHotelRequest(
                "신라호텔",
                "서울",
                "2bj@sinla.com",
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

    public AddRoomRequest addRoomRequest() {
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
