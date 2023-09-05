package com.babyblackdog.ddogdog.place.hotel.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.babyblackdog.ddogdog.place.exception.PlaceException;
import com.babyblackdog.ddogdog.place.hotel.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HumanName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.hotel.model.vo.PlaceName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlaceTest {

  @Test
  @DisplayName("유효한 숙소 정보로 생성할 수 있다.")
  void createPlaceEntity_CreateSuccess() {
    // Given & When
    Place place = new Place(
        new PlaceName("신라호텔"),
        new Province("서울"),
        1L,
        new PhoneNumber("01012341234"),
        new HumanName("이부진"),
        new BusinessName("신세계")
    );

    // Then
    assertThat(place.getAddressValue()).isEqualTo("서울");
  }

  @Test
  @DisplayName("유효하지 않은 숙소 정보로는 생성할 수 없다.")
  void createPlaceEntity_CreateFail() {
    // Given & When
    Exception exception = catchException(() -> new Place(
        new PlaceName(""),
        new Province(null),
        -1L,
        new PhoneNumber("0dfs"),
        new HumanName(""),
        new BusinessName(null)
    ));

    // Then
    assertThat(exception).isInstanceOf(PlaceException.class);
  }

}