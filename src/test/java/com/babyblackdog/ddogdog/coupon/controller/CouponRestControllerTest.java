package com.babyblackdog.ddogdog.coupon.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.babyblackdog.ddogdog.TestConfig;
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
                .andDo(document("post-review",
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

//    @Test
//    void createInstantCoupon() {
//    }
//
//    @Test
//    void getAvailableManualCoupon() {
//    }
//
//    @Test
//    void getAvailableInstantCoupon() {
//    }
//
//    @Test
//    void claimManualCoupon() {
//    }
//
//    @Test
//    void removeInstantCoupon() {
//    }
}