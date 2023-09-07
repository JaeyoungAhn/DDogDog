package com.babyblackdog.ddogdog.review.controller;

import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.hotel.model.vo.*;
import com.babyblackdog.ddogdog.place.hotel.repository.HotelRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
class ReviewRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[createReview] 유효한 리뷰 정보로 생성할 수 있다.")
    void createReview() throws Exception {
        // Given
        String provinceName = savedHotel.getAddressValue();

        // When & Then
        mockMvc.perform(get("/hotels")
                        .param("province", provinceName)
                        .param("page", "0")
                        .param("size", "5")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.placeResponses.content", Matchers.hasSize(1)))
                .andDo(print())
                .andDo(document("place-get-by-province",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("province").description("지역 이름"),
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("크기")
                        ),
                        responseFields(
                                fieldWithPath("placeResponses").type(OBJECT).description("숙소 응답"),
                                fieldWithPath("placeResponses.content").type(ARRAY).description("숙소 정보 배열"),
                                fieldWithPath("placeResponses.content[].id").type(NUMBER).description("숙소 아이디"),
                                fieldWithPath("placeResponses.content[].name").type(STRING).description("숙소 이름"),
                                fieldWithPath("placeResponses.content[].address").type(STRING)
                                        .description("숙소 지역 이름"),
                                fieldWithPath("placeResponses.content[].adminId").type(NUMBER)
                                        .description("관리자 아이디"),
                                fieldWithPath("placeResponses.content[].contact").type(STRING)
                                        .description("숙소 문의번호"),
                                fieldWithPath("placeResponses.content[].representative").type(STRING)
                                        .description("숙소 대표자명"),
                                fieldWithPath("placeResponses.content[].businessName").type(STRING)
                                        .description("숙소 사업장명"),

                                fieldWithPath("placeResponses.pageable").type(OBJECT)
                                        .description("pageable").ignored(),
                                fieldWithPath("placeResponses.pageable.pageNumber").type(NUMBER)
                                        .description("pageNumber"),
                                fieldWithPath("placeResponses.pageable.pageSize").type(NUMBER)
                                        .description("pageSize"),
                                fieldWithPath("placeResponses.pageable.sort").type(OBJECT)
                                        .description("pageSize"),
                                fieldWithPath("placeResponses.pageable.sort.empty").type(BOOLEAN)
                                        .description("pageSize"),
                                fieldWithPath("placeResponses.pageable.sort.unsorted").type(BOOLEAN)
                                        .description("pageSize"),
                                fieldWithPath("placeResponses.pageable.sort.sorted").type(BOOLEAN)
                                        .description("pageSize"),
                                fieldWithPath("placeResponses.pageable.offset").type(NUMBER)
                                        .description("offset"),
                                fieldWithPath("placeResponses.pageable.paged").type(BOOLEAN)
                                        .description("paged"),
                                fieldWithPath("placeResponses.pageable.unpaged").type(BOOLEAN)
                                        .description("unpaged"),
                                fieldWithPath("placeResponses.last").type(BOOLEAN).description("last")
                                        .ignored(),
                                fieldWithPath("placeResponses.totalPages").type(JsonFieldType.NUMBER)
                                        .description("totalPages"),
                                fieldWithPath("placeResponses.totalElements").type(JsonFieldType.NUMBER)
                                        .description("totalElements"),
                                fieldWithPath("placeResponses.first").type(BOOLEAN)
                                        .description("first").ignored(),
                                fieldWithPath("placeResponses.size").type(JsonFieldType.NUMBER).description("size")
                                        .ignored(),
                                fieldWithPath("placeResponses.number").type(JsonFieldType.NUMBER)
                                        .description("number").ignored(),
                                fieldWithPath("placeResponses.sort").type(OBJECT)
                                        .description("sort"),
                                fieldWithPath("placeResponses.sort.empty").type(BOOLEAN)
                                        .description("sort.empty").ignored(),
                                fieldWithPath("placeResponses.sort.unsorted").type(BOOLEAN)
                                        .description("sort.unsorted").ignored(),
                                fieldWithPath("placeResponses.sort.sorted").type(BOOLEAN)
                                        .description("sort.sorted").ignored(),
                                fieldWithPath("placeResponses.numberOfElements").type(JsonFieldType.NUMBER)
                                        .description("numberOfElements").ignored(),
                                fieldWithPath("placeResponses.empty").type(BOOLEAN)
                                        .description("empty").ignored()
                        )
                ));
    }

    @Test
    @DisplayName("숙소 아이디로 숙소를 조회한다.")
    void getPlace_ReadSuccess() throws Exception {
        // Given
        Long placeId = savedHotel.getId();

        // When & Then
        mockMvc.perform(get("/hotels/{placeId}", placeId)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value(savedHotel.getName()))
                .andDo(print())
                .andDo(document("place-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("placeId").description("숙소 아이디")
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

}