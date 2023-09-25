package com.babyblackdog.ddogdog.review.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.babyblackdog.ddogdog.TestConfig;
import com.babyblackdog.ddogdog.order.service.OrderReaderServiceImpl;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewModifyRequest;
import com.babyblackdog.ddogdog.review.controller.dto.ReviewRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(classes = TestConfig.class, properties = {"spring.profiles.active=test"})
class ReviewRestControllerTest {

    @Autowired
    private TestConfig config;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderReaderServiceImpl orderReaderService;

    @Test
    @DisplayName("리뷰를 정상적으로 등록한다.")
    void createReview_CreateSuccess() throws Exception {
        // Given
        ReviewRequest values = new ReviewRequest(
                config.getOrder().getId(),
                config.getHotelId(),
                config.getRoom().id(),
                "객실에 모기가 많았어요.",
                2.5
        );

        String jsonBody = objectMapper.writeValueAsString(values);
        given(orderReaderService.isStayOver(config.getOrder().getId())).willReturn(true);

        // When
        ResultActions result = mockMvc.perform(post("/reviews")
                .header("Authorization", "Bearer " + config.getJwtUserToken())
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        result.andExpect(status().isCreated())
                .andDo(document("post-review",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("orderId").type(NUMBER).description("주문 번호"),
                                fieldWithPath("hotelId").type(NUMBER).description("호텔 번호"),
                                fieldWithPath("roomId").type(NUMBER).description("객실 번호"),
                                fieldWithPath("content").type(STRING).description("리뷰 내용"),
                                fieldWithPath("rating").type(NUMBER).description("별점")
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("리뷰 아이디"),
                                fieldWithPath("roomId").type(NUMBER).description("객실 아이디"),
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("content").type(STRING).description("리뷰 내용"),
                                fieldWithPath("rating").type(NUMBER).description("리뷰 별점"),
                                fieldWithPath("createdDate").type(STRING).description("생성 시간")
                        )
                ));
    }

    @Test
    @DisplayName("리뷰를 수정한다.")
    void modifyReview_modifySuccess() throws Exception {
        // Given
        ReviewRequest values = new ReviewRequest(
                config.getOrder().getId(),
                config.getHotelId(),
                config.getRoom().id(),
                "객실에 모기가 많았어요.",
                2.5
        );

        String jsonBody = objectMapper.writeValueAsString(values);
        given(orderReaderService.isStayOver(config.getOrder().getId())).willReturn(true);

        ResultActions result = mockMvc.perform(post("/reviews")
                .header("Authorization", "Bearer " + config.getJwtUserToken())
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        String responseString = result.andReturn().getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseString, Map.class);
        Integer reviewId = (Integer) responseMap.get("id");

        ReviewModifyRequest modifyValues = new ReviewModifyRequest(
                "사장님의 피드백이 친절합니다."
        );

        String jsonModifyBody = objectMapper.writeValueAsString(modifyValues);

        // When
        ResultActions modifiedResult = mockMvc.perform(patch("/reviews/" + reviewId)
                .header("Authorization", "Bearer " + config.getJwtUserToken())
                .content(jsonModifyBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        modifiedResult.andExpect(status().isNoContent())
                .andDo(document("patch-review",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("content").type(STRING).description("리뷰 내용")
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("리뷰 아이디"),
                                fieldWithPath("roomId").type(NUMBER).description("객실 아이디"),
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("content").type(STRING).description("리뷰 내용"),
                                fieldWithPath("rating").type(NUMBER).description("리뷰 별점"),
                                fieldWithPath("createdDate").type(STRING).description("생성 시간")
                        )
                ));
    }

    @Test
    @DisplayName("내가 작성한 리뷰를 확인한다.")
    void findReviewsForMe_foundSuccessfully() throws Exception {
        // Given
        ReviewRequest values = new ReviewRequest(
                config.getOrder().getId(),
                config.getHotelId(),
                config.getRoom().id(),
                "객실에 모기가 많았어요.",
                2.5
        );

        String jsonBody = objectMapper.writeValueAsString(values);
        given(orderReaderService.isStayOver(config.getOrder().getId())).willReturn(true);

        ResultActions result = mockMvc.perform(post("/reviews")
                .header("Authorization", "Bearer " + config.getJwtUserToken())
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // When
        ResultActions foundResult = mockMvc.perform(get("/reviews/me")
                .header("Authorization", "Bearer " + config.getJwtUserToken()));

        // Then
        foundResult.andExpect(status().isOk())
                .andDo(document("get-review-me",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("reviewResponses").type(ARRAY).description("리뷰 배열")
                        ).andWithPrefix("reviewResponses.[].",
                                fieldWithPath("id").type(NUMBER).description("리뷰 아이디"),
                                fieldWithPath("roomId").type(NUMBER).description("객실 아이디"),
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("content").type(STRING).description("리뷰 내용"),
                                fieldWithPath("rating").type(NUMBER).description("리뷰 별점"),
                                fieldWithPath("createdDate").type(STRING).description("생성 시간")
                        )
                ));
    }

    @Test
    @DisplayName("호텔에 작성된 리뷰를 확인한다.")
    void findReviewsOfHotel_foundSuccessfully() throws Exception {
        // Given
        ReviewRequest values = new ReviewRequest(
                config.getOrder().getId(),
                config.getHotelId(),
                config.getRoom().id(),
                "객실에 모기가 많았어요.",
                2.5
        );

        String jsonBody = objectMapper.writeValueAsString(values);
        given(orderReaderService.isStayOver(config.getOrder().getId())).willReturn(true);

        ResultActions result = mockMvc.perform(post("/reviews")
                .header("Authorization", "Bearer " + config.getJwtUserToken())
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // When
        ResultActions foundResult = mockMvc.perform(get("/reviews/me")
                .param("hotelId", String.valueOf(config.getHotelId()))
                .header("Authorization", "Bearer " + config.getJwtUserToken()));

        // Then
        foundResult.andExpect(status().isOk())
                .andDo(document("get-review-hotel",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("reviewResponses").type(ARRAY).description("리뷰 배열")
                        ).andWithPrefix("reviewResponses.[].",
                                fieldWithPath("id").type(NUMBER).description("리뷰 아이디"),
                                fieldWithPath("roomId").type(NUMBER).description("객실 아이디"),
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("content").type(STRING).description("리뷰 내용"),
                                fieldWithPath("rating").type(NUMBER).description("리뷰 별점"),
                                fieldWithPath("createdDate").type(STRING).description("생성 시간")
                        )
                ));
    }
}