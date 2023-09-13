package com.babyblackdog.ddogdog.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
class ReviewRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("리뷰를 정상적으로 등록한다.")
    void createReview_CreateSuccess() throws Exception {
        // Given
        Map<String, Object> values = new HashMap<>();
        values.put("roomId", 1);
        values.put("content", "객실에 모기가 많았어요.");
        values.put("rating", 2.5);
        values.put("userId", 1);

        String jsonBody = objectMapper.writeValueAsString(values);

        // When
        ResultActions result = mockMvc.perform(post("/reviews")
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        result.andExpect(status().isCreated())
                .andDo(document("post-review",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("roomId").type(NUMBER).description("객실 번호"),
                                fieldWithPath("content").type(STRING).description("리뷰 내용"),
                                fieldWithPath("rating").type(NUMBER).description("리뷰 별점"),
                                fieldWithPath("userId").type(NUMBER).description("유저 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("리뷰 아이디"),
                                fieldWithPath("roomId").type(NUMBER).description("객실 아이디"),
                                fieldWithPath("content").type(STRING).description("리뷰 내용"),
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("rating").type(NUMBER).description("리뷰 별점"),
                                fieldWithPath("createdDate").type(STRING).description("생성 시간")
                        )
                ));
    }

}