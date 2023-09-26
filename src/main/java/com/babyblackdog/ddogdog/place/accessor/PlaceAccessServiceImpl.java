package com.babyblackdog.ddogdog.place.accessor;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.HOTEL_NOT_FOUND;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.ROOM_NOT_FOUND;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.global.exception.RatingException;
import com.babyblackdog.ddogdog.global.exception.RoomException;
import com.babyblackdog.ddogdog.place.accessor.vo.RoomSimpleResult;
import com.babyblackdog.ddogdog.place.model.Room;
import com.babyblackdog.ddogdog.place.repository.RatingRepository;
import com.babyblackdog.ddogdog.place.repository.RoomRepository;
import com.babyblackdog.ddogdog.place.service.PlaceService;
import com.babyblackdog.ddogdog.place.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlaceAccessServiceImpl implements PlaceAccessService {

    private final PlaceService placeService;
    private final RoomRepository roomRepository;
    private final RatingRepository ratingRepository;

    public PlaceAccessServiceImpl(PlaceService placeService, RoomRepository roomRepository,
            RatingRepository ratingRepository) {
        this.placeService = placeService;
        this.roomRepository = roomRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public RoomSimpleResult findRoomSimpleInfo(Long roomId) {
        RoomResult roomResult = placeService.findRoom(roomId);
        return RoomSimpleResult.of(roomResult);
    }

    @Override
    @Transactional
    public void addRatingScoreOfHotel(Long roomId, double ratingScore) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND));
        ratingRepository.findByHotelId(room.getHotel().getId())
                .orElseThrow(() -> new RatingException(HOTEL_NOT_FOUND))
                .addRatingScore(ratingScore);
    }

    @Override
    public List<Long> findRoomIdsOfHotel(Long hotelId) {
        return placeService.findRoomsOfHotel(hotelId, Pageable.unpaged())
                .stream()
                .map(RoomResult::id)
                .toList();
    }

    @Override
    public boolean isHotelValid(Long hotelId) {
        return placeService.existsHotel(hotelId);
    }

    @Override
    public HotelResult findHotelByEmail(Email email) {
        return placeService.findHotelByEmail(email);
    }
}
