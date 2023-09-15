package com.babyblackdog.ddogdog.place.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.babyblackdog.ddogdog.mapping.MappingService;
import com.babyblackdog.ddogdog.place.PlaceTestData;
import com.babyblackdog.ddogdog.place.controller.dto.AddHotelRequest;
import com.babyblackdog.ddogdog.place.controller.dto.AddRoomRequest;
import com.babyblackdog.ddogdog.place.model.Hotel;
import com.babyblackdog.ddogdog.place.model.Room;
import com.babyblackdog.ddogdog.place.repository.HotelRepository;
import com.babyblackdog.ddogdog.place.repository.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
class PlaceRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MappingService mappingService;

  @Autowired
  private HotelRepository hotelRepository;
  @Autowired
  private RoomRepository roomRepository;
  @Autowired
  private PlaceTestData placeTestData;
  @Autowired
  private ObjectMapper objectMapper;

  private Hotel savedHotel;
  private List<Room> savedRooms;

  @BeforeEach
  void setUp() {
    savedHotel = hotelRepository.save(placeTestData.getHotelEntity());
    List<Room> rooms = placeTestData.bindHotelToRooms(savedHotel);
    savedRooms = roomRepository.saveAll(rooms);
  }

  @Test
  @DisplayName("숙소 추가 요청으로 숙소를 등록한다.")
  void addHotel_CreateSuccess() throws Exception {
    // Given
    AddHotelRequest request = placeTestData.getAddHotelRequest();

    // When
    mockMvc.perform(post("/places")
            .content(objectMapper.writeValueAsString(request))
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.name").value(request.hotelName()))
        .andExpect(jsonPath("$.businessName").value(request.businessName()))
        .andDo(print())
        .andDo(document("post-hotel",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("hotelName").type(STRING).description("숙소 이름"),
                fieldWithPath("province").type(STRING).description("지역 이름"),
                fieldWithPath("adminId").type(NUMBER).description("관리자 아이디"),
                fieldWithPath("contact").type(STRING).description("문의처"),
                fieldWithPath("representative").type(STRING).description("대표자명"),
                fieldWithPath("businessName").type(STRING).description("사업장명")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("숙소 아이디"),
                fieldWithPath("name").type(STRING).description("숙소 이름"),
                fieldWithPath("address").type(STRING).description("숙소 지역 이름"),
                fieldWithPath("adminId").type(NUMBER).description("관리자 아이디"),
                fieldWithPath("contact").type(STRING).description("숙소 문의번호"),
                fieldWithPath("representative").type(STRING).description("숙소 대표자명"),
                fieldWithPath("businessName").type(STRING).description("숙소 사업장명")
            )
        ));
  }

  @Test
  @DisplayName("숙소 아이디로 숙소와 숙소 내 객실을 제거한다.")
  void removeHotel_DeleteSuccess() throws Exception {
    // Given
    Hotel hotel = hotelRepository.save(placeTestData.getHotelEntity());
    Room room = placeTestData.bindHotelToRooms(hotel).get(0);
    roomRepository.save(room);

    // When & Then
    mockMvc.perform(delete("/places/{hotelId}", hotel.getId())
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent())
        .andDo(print())
        .andDo(document("delete-hotel",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("hotelId").description("호텔 아이디")
            )
        ));
  }

  @Test
  @DisplayName("숙소 아이디로 숙소를 조회한다.")
  void getPlace_ReadSuccess() throws Exception {
    // Given
    Long hotelId = savedHotel.getId();

    // When & Then
    mockMvc.perform(get("/places/{hotelId}", hotelId)
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.name").value(savedHotel.getName()))
        .andDo(print())
        .andDo(document("hotel-get",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("hotelId").description("숙소 아이디")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("숙소 아이디"),
                fieldWithPath("name").type(STRING).description("숙소 이름"),
                fieldWithPath("address").type(STRING).description("숙소 지역 이름"),
                fieldWithPath("adminId").type(NUMBER).description("관리자 아이디"),
                fieldWithPath("contact").type(STRING).description("숙소 문의번호"),
                fieldWithPath("representative").type(STRING).description("숙소 대표자명"),
                fieldWithPath("businessName").type(STRING).description("숙소 사업장명")
            )
        ));
  }

  @Test
  @DisplayName("지역 이름으로 숙소를 조회한다.")
  void getPlacesByProvince_ReadSuccess() throws Exception {
    // Given
    String provinceName = savedHotel.getAddressValue();

    // When & Then
    mockMvc.perform(get("/places")
            .param("province", provinceName)
            .param("page", "0")
            .param("size", "5")
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.hotelResponses.content", Matchers.hasSize(1)))
        .andDo(print())
        .andDo(document("hotel-get-by-province",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            queryParameters(
                parameterWithName("province").description("지역 이름"),
                parameterWithName("page").description("페이지"),
                parameterWithName("size").description("크기")
            ),
            responseFields(
                fieldWithPath("hotelResponses").type(OBJECT).description("숙소 응답"),
                fieldWithPath("hotelResponses.content").type(ARRAY).description("숙소 정보 배열"),
                fieldWithPath("hotelResponses.content[].id").type(NUMBER).description("숙소 아이디"),
                fieldWithPath("hotelResponses.content[].name").type(STRING).description("숙소 이름"),
                fieldWithPath("hotelResponses.content[].address").type(STRING)
                    .description("숙소 지역 이름"),
                fieldWithPath("hotelResponses.content[].adminId").type(NUMBER)
                    .description("관리자 아이디"),
                fieldWithPath("hotelResponses.content[].contact").type(STRING)
                    .description("숙소 문의번호"),
                fieldWithPath("hotelResponses.content[].representative").type(STRING)
                    .description("숙소 대표자명"),
                fieldWithPath("hotelResponses.content[].businessName").type(STRING)
                    .description("숙소 사업장명"),

                fieldWithPath("hotelResponses.pageable").type(OBJECT)
                    .description("pageable").ignored(),
                fieldWithPath("hotelResponses.pageable.pageNumber").type(NUMBER)
                    .description("pageNumber"),
                fieldWithPath("hotelResponses.pageable.pageSize").type(NUMBER)
                    .description("pageSize"),
                fieldWithPath("hotelResponses.pageable.sort").type(OBJECT)
                    .description("pageSize"),
                fieldWithPath("hotelResponses.pageable.sort.empty").type(BOOLEAN)
                    .description("pageSize"),
                fieldWithPath("hotelResponses.pageable.sort.unsorted").type(BOOLEAN)
                    .description("pageSize"),
                fieldWithPath("hotelResponses.pageable.sort.sorted").type(BOOLEAN)
                    .description("pageSize"),
                fieldWithPath("hotelResponses.pageable.offset").type(NUMBER)
                    .description("offset"),
                fieldWithPath("hotelResponses.pageable.paged").type(BOOLEAN)
                    .description("paged"),
                fieldWithPath("hotelResponses.pageable.unpaged").type(BOOLEAN)
                    .description("unpaged"),
                fieldWithPath("hotelResponses.last").type(BOOLEAN).description("last")
                    .ignored(),
                fieldWithPath("hotelResponses.totalPages").type(JsonFieldType.NUMBER)
                    .description("totalPages"),
                fieldWithPath("hotelResponses.totalElements").type(JsonFieldType.NUMBER)
                    .description("totalElements"),
                fieldWithPath("hotelResponses.first").type(BOOLEAN)
                    .description("first").ignored(),
                fieldWithPath("hotelResponses.size").type(JsonFieldType.NUMBER).description("size")
                    .ignored(),
                fieldWithPath("hotelResponses.number").type(JsonFieldType.NUMBER)
                    .description("number").ignored(),
                fieldWithPath("hotelResponses.sort").type(OBJECT)
                    .description("sort"),
                fieldWithPath("hotelResponses.sort.empty").type(BOOLEAN)
                    .description("sort.empty").ignored(),
                fieldWithPath("hotelResponses.sort.unsorted").type(BOOLEAN)
                    .description("sort.unsorted").ignored(),
                fieldWithPath("hotelResponses.sort.sorted").type(BOOLEAN)
                    .description("sort.sorted").ignored(),
                fieldWithPath("hotelResponses.numberOfElements").type(JsonFieldType.NUMBER)
                    .description("numberOfElements").ignored(),
                fieldWithPath("hotelResponses.empty").type(BOOLEAN)
                    .description("empty").ignored()
            )
        ));
  }

  @Test
  @DisplayName("존재하는 호텔에 객실을 추가한다.")
  void addRoomOfHotel_CreateSuccess() throws Exception {
    // Given
    Long hotelId = savedHotel.getId();
    AddRoomRequest addRoomRequest = placeTestData.getAddRoomRequest();

    // When
    mockMvc.perform(post("/places/{hotelId}", hotelId)
            .content(objectMapper.writeValueAsString(addRoomRequest))
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.roomType").value(addRoomRequest.roomType()))
        .andExpect(jsonPath("$.occupancy").value(addRoomRequest.occupancy()))
        .andDo(print())
        .andDo(document("post-room",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("roomType").type(STRING).description("객실 타입"),
                fieldWithPath("description").type(STRING).description("객실 설명"),
                fieldWithPath("occupancy").type(NUMBER).description("객실 최대 수용 인원"),
                fieldWithPath("hasBed").type(BOOLEAN).description("객실 침대 여부"),
                fieldWithPath("hasAmenities").type(BOOLEAN).description("객실 생필품 여부"),
                fieldWithPath("smokingAvailable").type(BOOLEAN).description("객실 흡연 여부"),
                fieldWithPath("roomNumber").type(STRING).description("객실 방 번호"),
                fieldWithPath("point").type(NUMBER).description("객실 기본 요금")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("객실 아이디"),
                fieldWithPath("hotelName").type(STRING).description("호텔 이름"),
                fieldWithPath("roomType").type(STRING).description("객실 타입"),
                fieldWithPath("description").type(STRING).description("객실 설명"),
                fieldWithPath("occupancy").type(NUMBER).description("객실 최대 수용 인원"),
                fieldWithPath("hasBed").type(BOOLEAN).description("객실 침대 여부"),
                fieldWithPath("hasAmenities").type(BOOLEAN).description("객실 생필품 여부"),
                fieldWithPath("smokingAvailable").type(BOOLEAN).description("객실 흡연 가능 여부"),
                fieldWithPath("roomNumber").type(STRING).description("객실 방 번호"),
                fieldWithPath("point").type(NUMBER).description("객실 기본 가격"),
                fieldWithPath("reservationAvailable").type(BOOLEAN).description("객실 예약 가능 여부")
            )
        ));
  }

  @Test
  @DisplayName("존재하는 호텔에 객실을 제거한다.")
  void removeRoomOfHotel_DeleteSuccess() throws Exception {
    // Given
    Hotel hotel = hotelRepository.save(placeTestData.getHotelEntity());
    Room room = placeTestData.bindHotelToRooms(hotel).get(0);
    Room savedRoom = roomRepository.save(room);

    // When
    mockMvc.perform(delete("/places/{hotelId}/{roomId}", hotel.getId(), savedRoom.getId())
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent())
        .andDo(print())
        .andDo(document("delete-room",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("hotelId").description("호텔 아이디"),
                parameterWithName("roomId").description("객실 아이디")
            )
        ));

    // Then

  }

  @Test
  @DisplayName("특정 기간 동안 객실에 대한 정보를 조회한다.")
  void getRoomForDuration_Success() throws Exception {
    // Given
    LocalDate today = LocalDate.now();
    Room room = savedRooms.get(0);
    given(mappingService.isReservationAvailableForRoom(anyLong(), any(LocalDate.class),
        any(LocalDate.class)))
        .willReturn(true);

    // When & Then
    mockMvc.perform(get("/places/{hotelId}/{roomId}", room.getHotel().getId(), room.getId())
            .param("checkIn", today.toString())
            .param("checkOut", today.plusDays(1L).toString())
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(room.getId()))
        .andExpect(jsonPath("$.occupancy").value(room.getMaxOccupancy()))
        .andDo(print())
        .andDo(document("room-get-for-duration",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("hotelId").description("숙소 아이디"),
                parameterWithName("roomId").description("객실 아이디")
            ),
            queryParameters(
                parameterWithName("checkIn").description("체크인 날짜"),
                parameterWithName("checkOut").description("체크 아웃 날짜")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description("객실 아이디"),
                fieldWithPath("hotelName").type(STRING).description("호텔 이름"),
                fieldWithPath("roomType").type(STRING).description("객실 타입"),
                fieldWithPath("description").type(STRING).description("객실 설명"),
                fieldWithPath("occupancy").type(NUMBER).description("객실 최대 수용 인원"),
                fieldWithPath("hasBed").type(BOOLEAN).description("객실 침대 여부"),
                fieldWithPath("hasAmenities").type(BOOLEAN).description("객실 생필품 여부"),
                fieldWithPath("smokingAvailable").type(BOOLEAN).description("객실 흡연 가능 여부"),
                fieldWithPath("roomNumber").type(STRING).description("객실 방 번호"),
                fieldWithPath("point").type(NUMBER).description("객실 기본 가격"),
                fieldWithPath("reservationAvailable").type(BOOLEAN).description("객실 예약 가능 여부")
            )
        ));
  }

  @Test
  @DisplayName("특정 기간 동안 숙소에 대한 모든 객실 정보를 조회한다.")
  void getRoomsOfHotelForDuration_Success() throws Exception {
    // Given
    LocalDate today = LocalDate.now();
    given(mappingService.isReservationAvailableForRoom(anyLong(), any(LocalDate.class),
        any(LocalDate.class)))
        .willReturn(true);

    // When
    mockMvc.perform(get("/places/{hotelId}/rooms", savedHotel.getId())
            .param("checkIn", today.toString())
            .param("checkOut", today.plusDays(1L).toString())
            .param("page", "0")
            .param("size", "5")
            .accept(APPLICATION_JSON_VALUE)
            .contentType(APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.roomResponses.content[0].id").value(savedRooms.get(0).getId()))
        .andExpect(jsonPath("$.roomResponses.content[1].occupancy").value(
            savedRooms.get(1).getMaxOccupancy()))
        .andDo(print())
        .andDo(document("room-get-all-of-hotel-for-duration",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("hotelId").description("호텔 아이디")
            ),
            queryParameters(
                parameterWithName("checkIn").description("체크인 날짜"),
                parameterWithName("checkOut").description("체크 아웃 날짜"),
                parameterWithName("page").description("페이지"),
                parameterWithName("size").description("크기")
            ),
            responseFields(
                fieldWithPath("roomResponses").type(OBJECT).description("객실 응답"),
                fieldWithPath("roomResponses.content").type(ARRAY).description("객실 정보 배열"),
                fieldWithPath("roomResponses.content[].id").type(NUMBER).description("객실 아이디"),
                fieldWithPath("roomResponses.content[].hotelName").type(STRING)
                    .description("호텔 이름"),
                fieldWithPath("roomResponses.content[].roomType").type(STRING).description("객실 타입"),
                fieldWithPath("roomResponses.content[].description").type(STRING)
                    .description("객실 설명"),
                fieldWithPath("roomResponses.content[].occupancy").type(NUMBER)
                    .description("객실 최대 수용 인원"),
                fieldWithPath("roomResponses.content[].hasBed").type(BOOLEAN)
                    .description("객실 침대 여부"),
                fieldWithPath("roomResponses.content[].hasAmenities").type(BOOLEAN)
                    .description("객실 생필품 여부"),
                fieldWithPath("roomResponses.content[].smokingAvailable").type(BOOLEAN)
                    .description("객실 흡연 가능 여부"),
                fieldWithPath("roomResponses.content[].roomNumber").type(STRING)
                    .description("객실 방 번호"),
                fieldWithPath("roomResponses.content[].point").type(NUMBER).description("객실 기본 가격"),
                fieldWithPath("roomResponses.content[].reservationAvailable").type(BOOLEAN)
                    .description("객실 예약 가능 여부"),

                fieldWithPath("roomResponses.pageable").type(OBJECT)
                    .description("pageable").ignored(),
                fieldWithPath("roomResponses.pageable.pageNumber").type(NUMBER)
                    .description("pageNumber"),
                fieldWithPath("roomResponses.pageable.pageSize").type(NUMBER)
                    .description("pageSize"),
                fieldWithPath("roomResponses.pageable.sort").type(OBJECT)
                    .description("pageSize"),
                fieldWithPath("roomResponses.pageable.sort.empty").type(BOOLEAN)
                    .description("pageSize"),
                fieldWithPath("roomResponses.pageable.sort.unsorted").type(BOOLEAN)
                    .description("pageSize"),
                fieldWithPath("roomResponses.pageable.sort.sorted").type(BOOLEAN)
                    .description("pageSize"),
                fieldWithPath("roomResponses.pageable.offset").type(NUMBER)
                    .description("offset"),
                fieldWithPath("roomResponses.pageable.paged").type(BOOLEAN)
                    .description("paged"),
                fieldWithPath("roomResponses.pageable.unpaged").type(BOOLEAN)
                    .description("unpaged"),
                fieldWithPath("roomResponses.last").type(BOOLEAN).description("last")
                    .ignored(),
                fieldWithPath("roomResponses.totalPages").type(JsonFieldType.NUMBER)
                    .description("totalPages"),
                fieldWithPath("roomResponses.totalElements").type(JsonFieldType.NUMBER)
                    .description("totalElements"),
                fieldWithPath("roomResponses.first").type(BOOLEAN)
                    .description("first").ignored(),
                fieldWithPath("roomResponses.size").type(JsonFieldType.NUMBER).description("size")
                    .ignored(),
                fieldWithPath("roomResponses.number").type(JsonFieldType.NUMBER)
                    .description("number").ignored(),
                fieldWithPath("roomResponses.sort").type(OBJECT)
                    .description("sort"),
                fieldWithPath("roomResponses.sort.empty").type(BOOLEAN)
                    .description("sort.empty").ignored(),
                fieldWithPath("roomResponses.sort.unsorted").type(BOOLEAN)
                    .description("sort.unsorted").ignored(),
                fieldWithPath("roomResponses.sort.sorted").type(BOOLEAN)
                    .description("sort.sorted").ignored(),
                fieldWithPath("roomResponses.numberOfElements").type(JsonFieldType.NUMBER)
                    .description("numberOfElements").ignored(),
                fieldWithPath("roomResponses.empty").type(BOOLEAN)
                    .description("empty").ignored()
            )
        ));
  }
}