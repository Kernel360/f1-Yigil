package kr.co.yigil.login.interfaces.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.login.application.LoginFacade;
import kr.co.yigil.login.interfaces.dto.mapper.LoginMapper;
import kr.co.yigil.login.interfaces.dto.request.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(LoginApiController.class)
@AutoConfigureRestDocs
public class LoginApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private LoginFacade loginFacade;

    @MockBean
    private LoginMapper loginMapper;

    @InjectMocks
    private LoginApiController loginApiController;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @DisplayName("카카오 로그인이 요청 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenLogin_thenReturns200AndLoginResponse() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/api/v1/login")
                        .header("Authorization", "Bearer mockAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"id\":123, \"nickname\":\"TestUser\", \"profileImageUrl\":\"test.jpg\", \"email\":\"test@example.com\", \"provider\":\"kakao\"}")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("로그인 성공")))
                .andDo(document("login/login",
                        requestHeaders(
                                headerWithName("Authorization").description("인증 토큰")
                        ),
                        requestFields(
                                fieldWithPath("id").description("사용자 ID"),
                                fieldWithPath("nickname").description("사용자 닉네임"),
                                fieldWithPath("profileImageUrl").description("사용자 프로필 이미지 URL"),
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("provider").description("인증 제공자")
                        ),
                        responseFields(
                                fieldWithPath("message").description("로그인 성공 메시지")
                        )
                ));

        verify(loginMapper).toCommandLoginRequest(any(LoginRequest.class));
        verify(loginFacade).executeLoginStrategy(any(), any());
        assertThat(session.getAttribute("memberId")).isNotNull();
    }

    @DisplayName("로그아웃 요청이 들어왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenLogout_thenReturns200AndLogoutResponse() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", 1L);

        mockMvc.perform(get("/api/v1/logout").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("로그아웃 성공")))
                .andDo(document("login/logout",
                        responseFields(
                                fieldWithPath("message").description("로그아웃 성공 메시지")
                        )
                ));

        assertThat(session.isInvalid()).isTrue();
    }


}