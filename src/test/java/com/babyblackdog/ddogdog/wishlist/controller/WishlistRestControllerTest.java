package com.babyblackdog.ddogdog.wishlist.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.babyblackdog.ddogdog.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(classes = TestConfig.class, properties = {"spring.profiles.active=test"})
class WishlistRestControllerTest {

    @Autowired
    private TestConfig config;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("찜을 등록한다.")
    void createWishlist_createdSuccessfully() throws Exception {
        // Given && When
        ResultActions result = mockMvc.perform(put("/wishlist")
                .header("Authorization", "Bearer " + config.getJwtUserToken())
                .param("hotelId", String.valueOf(config.getHotelId()))
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        result.andExpect(status().isCreated())
                .andDo(document("put-wishlist",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("placeId").type(NUMBER).description("호텔 아이디")
                        )
                ));
    }

    @Test
    @DisplayName("찜을 해지한다.")
    void removeWishlist_deletedSuccessfully() throws Exception {
        // Given
        ResultActions result = mockMvc.perform(put("/wishlist")
                .header("Authorization", "Bearer " + config.getJwtUserToken())
                .param("hotelId", String.valueOf(config.getHotelId()))
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        String responseString = result.andReturn().getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseString, Map.class);
        Integer placeId = (Integer) responseMap.get("placeId");

        // When
        ResultActions resultForDeletion = mockMvc.perform(delete("/wishlist/" + placeId)
                .header("Authorization", "Bearer " + config.getJwtUserToken()));

        // Then
        resultForDeletion.andExpect(status().isNoContent())
                .andDo(document("delete-wishlist",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("내가 등록한 찜들을 확인한다.")
    void getWishlistForMe_foundSuccessfully() throws Exception {
        // Given
        mockMvc.perform(put("/wishlist")
                .header("Authorization", "Bearer " + config.getJwtUserToken())
                .param("hotelId", String.valueOf(config.getHotelId()))
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        int page = 0;
        int size = 1;

        // When
        ResultActions resultForMe = mockMvc.perform(get("/wishlist/me")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .header("Authorization", "Bearer " + config.getJwtUserToken()));

        // Then
        resultForMe.andExpect(status().isOk())
                .andDo(document("get-wishlist-me",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("wishlistResponses").type(ARRAY).description("리뷰 배열")
                        ).andWithPrefix("wishlistResponses.[].",
                                fieldWithPath("placeId").type(NUMBER).description("호테 아이디")
                        )
                ));
    }

}