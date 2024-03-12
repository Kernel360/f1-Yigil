package kr.co.yigil.admin.interfaces;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.yigil.admin.application.AdminFacade;
import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.admin.AdminCommand;
import kr.co.yigil.admin.domain.admin.AdminInfo;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpCommand;
import kr.co.yigil.admin.interfaces.controller.AdminApiController;
import kr.co.yigil.admin.interfaces.dto.mapper.AdminMapper;
import kr.co.yigil.admin.interfaces.dto.mapper.AdminSignupMapper;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignupRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpRejectRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminDetailInfoResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignUpsResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignupResponse;
import kr.co.yigil.auth.dto.JwtToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
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
    private AdminFacade adminFacade;

    @MockBean
    private AdminMapper adminMapper;

    @MockBean
    private AdminSignupMapper adminSignupMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("회원가입이 요청 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenSendSignUpRequest_thenReturns200AndAdminSignupResponse() throws Exception {
        AdminSignupRequest request = new AdminSignupRequest();
        AdminSignupResponse response = new AdminSignupResponse();
        AdminSignUpCommand.AdminSignUpRequest command = mock(
                AdminSignUpCommand.AdminSignUpRequest.class);
        given(adminSignupMapper.toCommand(request)).willReturn(command);

        mockMvc.perform(post("/api/v1/admins/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@test.com\", \"nickname\": \"TestUser\"}"))
                .andExpect(status().isOk());
    }


    @DisplayName("회원가입 요청 리스트가 요청 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetSignUpRequestList_thenReturns200AndAdminSignUpsResponse() throws Exception {
        AdminSignUpListRequest request = new AdminSignUpListRequest();
        Page<AdminSignUp> adminSignUps = Page.empty();
        AdminSignUpsResponse response = new AdminSignUpsResponse();

        given(adminFacade.getSignUpRequestList(request)).willReturn(adminSignUps);
        given(adminSignupMapper.toResponse(adminSignUps)).willReturn(response);

        mockMvc.perform(get("/api/v1/admins/signup/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("회원가입이 승인 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenAcceptSignUp_thenReturns200AndSignUpAcceptResponse() throws Exception {
        SignUpAcceptRequest request = new SignUpAcceptRequest();

        mockMvc.perform(post("/api/v1/admins/signup/accept")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @DisplayName("회원가입이 거절 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenRejectSignUp_thenReturns200AndSignUpRejectResponse() throws Exception {
        SignUpRejectRequest request = new SignUpRejectRequest();

        mockMvc.perform(post("/api/v1/admins/signup/reject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @DisplayName("로그인이 요청 되었을 때 200 응답과 Jwt 토큰이 잘 반환되는지")
    @Test
    void whenLogin_thenReturns200AndJwtToken() throws Exception {
        AdminCommand.LoginRequest command = mock(AdminCommand.LoginRequest.class);
        JwtToken token = new JwtToken("mockType", "mockAccessToken", "mockRefreshToken");

        given(adminFacade.signIn(command)).willReturn(token);

        mockMvc.perform(post("/api/v1/admins/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":  \"test@test,com\", \"password\":  \"testPassword\"}"))
                .andExpect(status().isOk());
    }

    @DisplayName("어드민 정보가 요청 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    @WithMockUser(username = "tester@tester.com")
    void whenGetMemberInfo_thenReturns200AndAdminInfoResponse() throws Exception {
        AdminInfo.AdminInfoResponse response = mock(AdminInfo.AdminInfoResponse.class);

        given(response.getNickname()).willReturn("tester");
        given(response.getProfileUrl()).willReturn("example.url");

        given(adminFacade.getAdminInfoByEmail("tester@tester.com")).willReturn(response);
        given(adminMapper.toResponse(response)).willReturn(
                new AdminInfoResponse("tester", "example.url"));

        mockMvc.perform(get("/api/v1/admins/info")
                        .with(SecurityMockMvcRequestPostProcessors.user("tester").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(response.getNickname()))
                .andExpect(jsonPath("$.profile_url").value(response.getProfileUrl()));
    }

    @DisplayName("어드민 상세 정보가 요청 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    @WithMockUser(username = "tester@tester.com")
    void whenGetMemberDetailInfo_thenReturns200AndAdminDetailInfoResponse() throws Exception {
        AdminInfo.AdminDetailInfoResponse response = mock(AdminInfo.AdminDetailInfoResponse.class);

        given(response.getNickname()).willReturn("tester");
        given(response.getProfileUrl()).willReturn("example.url");
        given(response.getEmail()).willReturn("tester@tester.com");
        given(response.getPassword()).willReturn("password");

        given(adminFacade.getAdminDetailInfoByEmail("tester@tester.com")).willReturn(response);
        given(adminMapper.toResponse(response)).willReturn(
                new AdminDetailInfoResponse("tester", "example.url", "tester@tester.com",
                        "password"));

        mockMvc.perform(get("/api/v1/admins/detail-info")
                        .with(SecurityMockMvcRequestPostProcessors.user("tester").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(response.getNickname()))
                .andExpect(jsonPath("$.email").value(response.getEmail()))
                .andExpect(jsonPath("$.password").value(response.getPassword()))
                .andExpect(jsonPath("$.profile_url").value(response.getProfileUrl()));
    }

    @DisplayName("어드민 프로필 이미지가 수정 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    @WithMockUser(username = "tester@tester.com")
    void whenUpdateProfileImage_thenReturns200AndAdminProfileImageUpdateResponse() throws Exception {

        mockMvc.perform(post("/api/v1/admins/profile-image")
                        .with(SecurityMockMvcRequestPostProcessors.user("tester").roles("USER"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(adminFacade).updateProfileImage(anyString(), any());
    }

    @DisplayName("어드민 비밀번호가 수정 되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    @WithMockUser(username = "tester@tester.com")
    void whenUpdatePassword_thenReturns200AndAdminPasswordUpdateResponse() throws Exception {
        mockMvc.perform(post("/api/v1/admins/password")
                        .with(SecurityMockMvcRequestPostProcessors.user("tester").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"existingPassword\": \"testPassword\", \"newPassword\": \"newPassword\"}"))
                .andExpect(status().isOk());

        verify(adminFacade).updatePassword(anyString(), any());
    }
}
