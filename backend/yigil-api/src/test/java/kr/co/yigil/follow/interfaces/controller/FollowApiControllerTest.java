package kr.co.yigil.follow.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.follow.application.FollowFacade;
import kr.co.yigil.follow.interfaces.controller.FollowApiController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(FollowApiController.class)
@AutoConfigureRestDocs
public class FollowApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private FollowFacade followFacade;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @DisplayName("팔로우가 요청되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenFollow_thenReturns200AndFollowResponse() throws Exception {
        Long memberId = 2L;

        mockMvc.perform(post("/api/v1/follow/{member_id}", memberId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"팔로우 성공\"}"))
                .andDo(document("follows/follow",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("member_id").description("팔로우할 회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메시지")
                        )
                ));

        verify(followFacade).follow(anyLong(), anyLong());
    }

    @DisplayName("언팔로우가 요청되었을 때 200 응답과 Response가 잘 반환되는지")
    @Test
    void whenUnfollow_thenReturns200AndUnfollowResponse() throws Exception {
        Long memberId = 2L;

        mockMvc.perform(post("/api/v1/unfollow/{member_id}" , memberId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"언팔로우 성공\"}"))
                .andDo(document("follows/unfollow",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("member_id").description("언팔로우할 회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메시지")
                        )
                ));

        verify(followFacade).unfollow(anyLong(), anyLong());
    }

}