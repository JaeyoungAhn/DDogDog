package com.babyblackdog.ddogdog.place.hotel.service;

import static com.babyblackdog.ddogdog.global.error.HotelErrorCode.HOTEL_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HotelName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HumanName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.hotel.service.dto.HotelResult;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@Transactional
class HotelServiceImplTest {

  @Autowired
  private HotelService hotelService;

  @Autowired
  private HotelRepository hotelRepository;

  private Hotel hotel;

  @BeforeEach
  void setUp() {
    this.hotel = new Hotel(
        new HotelName("신라호텔"),
        new Province("서울"),
        1L,
        new PhoneNumber("01012341234"),
        new HumanName("이부진"),
        new BusinessName("신세계")
    );
  }

  @AfterEach
  void tearDown() {
    hotelRepository.deleteAll();
  }

  @Test
  @DisplayName("[findPlaceByProvince] 유효한 지역 이름으로 숙소를 조회하면 성공한다.")
  void findPlaceByProvince_ReadSuccess() {
    // Given
    Hotel savedHotel = hotelRepository.save(hotel);

    // When
    Page<HotelResult> result = hotelService.findHotelByProvince(
        savedHotel.getAddress(),
        PageRequest.of(0, 2));

    // Then
    assertThat(result)
        .isNotNull()
        .isNotEmpty();
    assertThat(result.getContent()).hasSize(1);
  }

  @Test
  @DisplayName("[findPlaceByProvince] 유효하지 않은 지역 이름으로 숙소를 조회하면 실패한다.")
  void findPlaceByProvince_ReadException() {
    // Given
    Province invalidProvince = new Province("평양");

    // When
    Page<HotelResult> result = hotelService.findHotelByProvince(
        invalidProvince,
        PageRequest.of(0, 2));

    // Then
    assertThat(result)
        .isNotNull()
        .isEmpty();
  }

  @Test
  @DisplayName("[findPlaceById] 유효한 숙소 아이디를 이용해 숙소를 조회하면 성공한다.")
  void findPlaceById_ReadSuccess() {
    // Given
    Hotel savedHotel = hotelRepository.save(hotel);

    // When
    HotelResult result = hotelService.findHotelById(savedHotel.getId());

    // Then
    assertThat(result).isEqualTo(HotelResult.of(savedHotel));
  }

  @Test
  @DisplayName("[findPlaceById] 유효하지 않은 숙소 아이디를 이용해 숙소를 조회하면 실패한다.")
  void findPlaceById_ReadException() {
    // Given
    Long invalidId = 1L;

    // When & Then
    assertThatThrownBy(() -> hotelService.findHotelById(invalidId))
        .isInstanceOf(HotelException.class)
        .hasMessage(HOTEL_NOT_FOUND.toString());
  }

}