package kr.co.yigil.login.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.login.application.LoginStrategyManager;
import kr.co.yigil.login.application.strategy.LoginStrategy;
import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private LoginStrategyManager loginStrategyManager;

    @Mock
    private LoginStrategy mockStrategy;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("로그인이 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenLogin_thenReturns200AndLoginResponse() throws Exception {
        String provider = "kakao";
        LoginRequest loginRequest = new LoginRequest();
        LoginResponse mockResponse = new LoginResponse();

        given(loginStrategyManager.getLoginStrategy(provider)).willReturn(mockStrategy);
        given(mockStrategy.login(loginRequest, "mockAccessToken", null)).willReturn(mockResponse);

        mockMvc.perform(post("/api/v1/login/" + provider)
                        .header("Authorization", "Bearer mockAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":123, \"nickname\":\"TestUser\", \"profileImageUrl\":\"test.jpg\", \"email\":\"test@example.com\"}"))
                .andExpect(status().isOk());
    }
}

