package com.babyblackdog.ddogdog.place.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.babyblackdog.ddogdog.global.exception.HotelException;
import com.babyblackdog.ddogdog.place.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.model.vo.HotelName;
import com.babyblackdog.ddogdog.place.model.vo.HumanName;
import com.babyblackdog.ddogdog.place.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.model.vo.Province;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HotelTest {

  @Test
  @DisplayName("유효한 숙소 정보로 생성할 수 있다.")
  void createPlaceEntity_CreateSuccess() {
    // Given & When
    Hotel hotel = new Hotel(
        new HotelName("신라호텔"),
        new Province("서울"),
        1L,
        new PhoneNumber("010-1234-1234"),
        new HumanName("이부진"),
        new BusinessName("신세계")
    );

    // Then
    assertThat(hotel.getAddressValue()).isEqualTo("서울");
  }

  @Test
  @DisplayName("유효하지 않은 숙소 정보로는 생성할 수 없다.")
  void createPlaceEntity_CreateFail() {
    // Given & When
    Exception exception = catchException(() -> new Hotel(
        new HotelName(""),
        new Province(null),
        -1L,
        new PhoneNumber("0dfs"),
        new HumanName(""),
        new BusinessName(null)
    ));

    // Then
    assertThat(exception).isInstanceOf(HotelException.class);
  }

}