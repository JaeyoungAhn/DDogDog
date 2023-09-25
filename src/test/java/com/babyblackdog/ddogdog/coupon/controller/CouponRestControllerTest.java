package com.babyblackdog.ddogdog.coupon.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
import com.babyblackdog.ddogdog.coupon.controller.dto.request.InstantCouponCreationRequest;
import com.babyblackdog.ddogdog.coupon.controller.dto.request.ManualCouponClaimRequest;
import com.babyblackdog.ddogdog.coupon.controller.dto.request.ManualCouponCreationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
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
class CouponRestControllerTest {


    @Autowired
    private TestConfig config;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("수동 쿠폰을 정상적으로 등록한다.")
    void createManualCoupon_successfullyCreated() throws Exception {
        // Given
        ManualCouponCreationRequest values = new ManualCouponCreationRequest(
                "10프로 할인 쿠폰",
                "percent",
                10.0,
                "EWQWDZXCA",
                50L,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );

        String jsonBody = objectMapper.writeValueAsString(values);

        // When
        ResultActions result = mockMvc.perform(post("/coupons/manual")
                .header("Authorization", "Bearer " + config.getJwtAdminToken())
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        result.andExpect(status().isCreated())
                .andDo(document("post-manual-coupon-creation",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("couponName").type(STRING).description("쿠폰 이름"),
                                fieldWithPath("discountType").type(STRING).description("할인 타입"),
                                fieldWithPath("discountValue").type(NUMBER).description("할인액"),
                                fieldWithPath("promoCode").type(STRING).description("프로모션 코드"),
                                fieldWithPath("issueCount").type(NUMBER).description("발행 수"),
                                fieldWithPath("startDate").type(STRING).description("시작일"),
                                fieldWithPath("endDate").type(STRING).description("종료일")
                        ),
                        responseFields(
                                fieldWithPath("couponId").type(NUMBER).description("쿠폰 아이디"),
                                fieldWithPath("couponName").type(STRING).description("쿠폰 이름"),
                                fieldWithPath("couponType").type(STRING).description("쿠폰 타입"),
                                fieldWithPath("discountType").type(STRING).description("할인 타입"),
                                fieldWithPath("discountValue").type(NUMBER).description("할인액"),
                                fieldWithPath("promoCode").type(STRING).description("프로모션 코드"),
                                fieldWithPath("issueCount").type(NUMBER).description("발행 수"),
                                fieldWithPath("remainingCount").type(NUMBER).description("남은 수"),
                                fieldWithPath("startDate").type(STRING).description("시작일"),
                                fieldWithPath("endDate").type(STRING).description("종료일")
                        )
                ));
    }

    @Test
    @DisplayName("즉시 쿠폰을 정상적으로 등록한다.")
    void createInstantCoupon_createdSuccessfully() throws Exception {
        // Given
        InstantCouponCreationRequest values = new InstantCouponCreationRequest(
                config.getRoom().id(),
                "디럭스룸 3천원 할인 쿠폰",
                "amount",
                3_000.0,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );

        String jsonBody = objectMapper.writeValueAsString(values);

        // When
        ResultActions result = mockMvc.perform(post("/coupons/instant")
                .header("Authorization", "Bearer " + config.getJwtOwnerToken())
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        result.andExpect(status().isCreated())
                .andDo(document("post-instant-coupon-creation",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("roomId").type(NUMBER).description("객실 번호"),
                                fieldWithPath("couponName").type(STRING).description("쿠폰 이름"),
                                fieldWithPath("discountType").type(STRING).description("할인 타입"),
                                fieldWithPath("discountValue").type(NUMBER).description("할인액"),
                                fieldWithPath("startDate").type(STRING).description("시작일"),
                                fieldWithPath("endDate").type(STRING).description("종료일")
                        ),
                        responseFields(
                                fieldWithPath("couponId").type(NUMBER).description("쿠폰 아이디"),
                                fieldWithPath("roomId").type(NUMBER).description("객실 번호"),
                                fieldWithPath("couponName").type(STRING).description("쿠폰 이름"),
                                fieldWithPath("couponType").type(STRING).description("쿠폰 타입"),
                                fieldWithPath("discountType").type(STRING).description("할인 타입"),
                                fieldWithPath("discountValue").type(NUMBER).description("할인액"),
                                fieldWithPath("startDate").type(STRING).description("시작일"),
                                fieldWithPath("endDate").type(STRING).description("종료일")
                        )
                ));
    }

    @Test
    @DisplayName("소유 중인 수동 할인 쿠폰을 확인한다.")
    void getAvailableManualCoupon_foundSuccessfully() throws Exception {
        // Given && When
        ResultActions result = mockMvc.perform(get("/coupons/manual/available")
                .header("Authorization", "Bearer " + config.getJwtUserToken()));

        // Then
        result.andExpect(status().isOk())
                .andDo(document("get-manual-coupon-find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("manualCouponResponses").type(ARRAY).description("수동 쿠폰 응답")
                        ).andWithPrefix("manualCouponResponses.[].",
                                fieldWithPath("couponUsageId").type(NUMBER).description("쿠폰 사용 아이디"),
                                fieldWithPath("couponName").type(STRING).description("쿠폰 이름"),
                                fieldWithPath("couponType").type(STRING).description("쿠폰 타입"),
                                fieldWithPath("discountType").type(STRING).description("할인 타입"),
                                fieldWithPath("discountValue").type(NUMBER).description("할인액"),
                                fieldWithPath("endDate").type(STRING).description("종료일")
                        )
                ));
    }

    @Test
    @DisplayName("호텔이 제공하는 즉시 할인 쿠폰을 확인한다.")
    void getAvailableInstantCoupon_foundSuccessfully() throws Exception {
        // Given && When
        ResultActions result = mockMvc.perform(get("/coupons/instant")
                .param("hotelId", config.getHotelId().toString())
                .header("Authorization", "Bearer " + config.getJwtUserToken()));

        // Then
        result.andExpect(status().isOk())
                .andDo(document("get-instant-coupon-find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("instantCouponResponses").type(ARRAY).description("즉시 쿠폰 응답")
                        ).andWithPrefix("instantCouponResponses.[].",
                                fieldWithPath("couponId").type(NUMBER).description("쿠폰 아이디"),
                                fieldWithPath("roomId").type(NUMBER).description("객실 번호"),
                                fieldWithPath("couponName").type(STRING).description("쿠폰 이름"),
                                fieldWithPath("couponType").type(STRING).description("쿠폰 타입"),
                                fieldWithPath("discountType").type(STRING).description("할인 타입"),
                                fieldWithPath("discountValue").type(NUMBER).description("할인액"),
                                fieldWithPath("endDate").type(STRING).description("종료일")
                        )
                ));
    }

    @Test
    @DisplayName("수동 쿠폰을 수령한다.")
    void claimManualCoupon_claimedSuccessfully() throws Exception {
        // Given
        ManualCouponClaimRequest values = new ManualCouponClaimRequest(
                config.getManualCoupon().promoCode()
        );

        String jsonBody = objectMapper.writeValueAsString(values);

        // When
        ResultActions result = mockMvc.perform(post("/coupons/manual/claim")
                .header("Authorization", "Bearer " + config.getJwtAdminToken())
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        result.andExpect(status().isCreated())
                .andDo(document("post-manual-coupon-claim",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("promoCode").type(STRING).description("프로모션 코드")
                        ),
                        responseFields(
                                fieldWithPath("couponUsageId").type(NUMBER).description("쿠폰 사용 아이디"),
                                fieldWithPath("couponName").type(STRING).description("쿠폰 이름"),
                                fieldWithPath("couponType").type(STRING).description("쿠폰 타입"),
                                fieldWithPath("discountType").type(STRING).description("할인 타입"),
                                fieldWithPath("discountValue").type(NUMBER).description("할인액"),
                                fieldWithPath("endDate").type(STRING).description("종료일")
                        )
                ));
    }

    @Test
    @DisplayName("즉시 적용 쿠폰을 삭제한다.")
    void removeInstantCoupon_deletedSuccessfully() throws Exception {
        // Given && When
        ResultActions result = mockMvc.perform(delete("/coupons/instant/" + config.getInstantCoupon().couponId())
                .header("Authorization", "Bearer " + config.getJwtOwnerToken()));

        // Then
        result.andExpect(status().isNoContent())
                .andDo(document("delete-instant-coupon",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        )
                ));
    }
}