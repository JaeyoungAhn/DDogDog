package com.babyblackdog.ddogdog.place.hotel.service;

import static com.babyblackdog.ddogdog.place.exception.ErrorCode.PLACE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.babyblackdog.ddogdog.place.exception.PlaceException;
import com.babyblackdog.ddogdog.place.hotel.model.Place;
import com.babyblackdog.ddogdog.place.hotel.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.HumanName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.hotel.model.vo.PlaceName;
import com.babyblackdog.ddogdog.place.hotel.model.vo.Province;
import com.babyblackdog.ddogdog.place.hotel.repository.PlaceRepository;
import com.babyblackdog.ddogdog.place.hotel.service.dto.PlaceResult;
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
class PlaceServiceImplTest {

  @Autowired
  private PlaceService placeService;

  @Autowired
  private PlaceRepository placeRepository;

  private Place place;

  @BeforeEach
  void setUp() {
    this.place = new Place(
        new PlaceName("신라호텔"),
        new Province("서울"),
        1L,
        new PhoneNumber("01012341234"),
        new HumanName("이부진"),
        new BusinessName("신세계")
    );
  }

  @AfterEach
  void tearDown() {
    placeRepository.deleteAll();
  }

  @Test
  @DisplayName("[findPlaceByProvince] 유효한 지역 이름으로 숙소를 조회하면 성공한다.")
  void findPlaceByProvince_ReadSuccess() {
    // Given
    Place savedPlace = placeRepository.save(place);

    // When
    Page<PlaceResult> result = placeService.findPlaceByProvince(
        savedPlace.getAddress(),
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
    Page<PlaceResult> result = placeService.findPlaceByProvince(
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
    Place savedPlace = placeRepository.save(place);

    // When
    PlaceResult result = placeService.findPlaceById(savedPlace.getId());

    // Then
    assertThat(result).isEqualTo(PlaceResult.of(savedPlace));
  }

  @Test
  @DisplayName("[findPlaceById] 유효하지 않은 숙소 아이디를 이용해 숙소를 조회하면 실패한다.")
  void findPlaceById_ReadException() {
    // Given
    Long invalidId = 1L;

    // When & Then
    assertThatThrownBy(() -> placeService.findPlaceById(invalidId))
        .isInstanceOf(PlaceException.class)
        .hasMessage(PLACE_NOT_FOUND.toString());
  }

}