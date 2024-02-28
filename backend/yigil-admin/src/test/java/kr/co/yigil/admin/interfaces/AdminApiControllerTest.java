package kr.co.yigil.admin.interfaces;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignupRequest;
import kr.co.yigil.admin.interfaces.dto.request.LoginRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignupResponse;
import kr.co.yigil.admin.interfaces.controller.AdminApiController;
import kr.co.yigil.auth.dto.JwtToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminApiController.class)
public class AdminApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @InjectMocks
    private AdminApiController adminApiController;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("회원가입이 요청 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenSendSignUpRequest_thenReturns200AndAdminSignupResponse() throws Exception {
        AdminSignupRequest request = new AdminSignupRequest();
        AdminSignupResponse response = new AdminSignupResponse();

        given(adminService.sendSignUpRequest(request)).willReturn(response);

        mockMvc.perform(post("/admin/api/v1/admins/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@test.com\", \"nickname\": \"TestUser\"}"))
                .andExpect(status().isOk());
    }

    @DisplayName("로그인이 요청 되었을 때 200 응답과 Jwt 토큰이 잘 반환되는지")
    @Test
    void whenLogin_thenReturns200AndJwtToken() throws Exception {
        LoginRequest request = new LoginRequest();
        JwtToken token = new JwtToken("mockType", "mockAccessToken", "mockRefreshToken");

        given(adminService.signIn(request)).willReturn(token);

        mockMvc.perform(post("/admin/api/v1/admins/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":  \"test@test,com\", \"password\":  \"testPassword\"}"))
                .andExpect(status().isOk());
    }

    @DisplayName("어드민 정보가 요청 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    @WithMockUser(username="tester")
    void whenGetMemberInfo_thenReturns200AndAdminInfoResponse() throws Exception {
        AdminInfoResponse response = new AdminInfoResponse();
        response.setUsername("tester");
        response.setProfileUrl("example.url");

        given(adminService.getAdminInfoByEmail("tester")).willReturn(response);

        mockMvc.perform(get("/admin/api/v1/admins/info")
                        .with(SecurityMockMvcRequestPostProcessors.user("tester").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(response.getUsername()))
                .andExpect(jsonPath("$.profile_url").value(response.getProfileUrl()));
    }
}
