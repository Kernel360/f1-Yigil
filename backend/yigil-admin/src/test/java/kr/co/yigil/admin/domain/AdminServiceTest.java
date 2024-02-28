package kr.co.yigil.admin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import kr.co.yigil.admin.domain.admin.Admin;
import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUp;
import kr.co.yigil.admin.infrastructure.admin.AdminRepository;
import kr.co.yigil.admin.infrastructure.adminSignUp.AdminSignUpRepository;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignupRequest;
import kr.co.yigil.admin.interfaces.dto.request.LoginRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignUpListResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignupResponse;
import kr.co.yigil.auth.application.JwtTokenProvider;
import kr.co.yigil.auth.dto.JwtToken;
import kr.co.yigil.global.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminSignUpRepository adminSignUpRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("로그인 메서드가 잘 동작하는지")
    @Test
    void signInTest() {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");
        JwtToken expectedToken = new JwtToken("Bearer", "accessToken", "refreshToken");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(expectedToken);

        JwtToken result = adminService.signIn(loginRequest);

        assertThat(result.getAccessToken()).isEqualTo("accessToken");
        assertThat(result.getRefreshToken()).isEqualTo("refreshToken");

    }

    @DisplayName("회원가입 요청 메서드가 잘 동작하는지")
    @Test
    void sendSignUpRequestTest() {
        AdminSignupRequest singupRequest = new AdminSignupRequest("user@example.com", "user");
        when(adminSignUpRepository.save(any(AdminSignUp.class))).thenReturn(null);

        AdminSignupResponse response = adminService.sendSignUpRequest(singupRequest);

        assertThat(response.getMessage()).isEqualTo("회원가입 요청이 완료되었습니다.");
    }

    @DisplayName("이미 존재하는 어드민 정보로 회원가입 요청 시 예외가 잘 발생하는지")
    @Test
    void sendSignUpRequest_FailureAlreadyExists() {
        AdminSignupRequest request = new AdminSignupRequest("email@example.com", "nickname");

        when(adminRepository.existsByEmailOrNickname(request.getEmail(), request.getNickname())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> adminService.sendSignUpRequest(request));
    }

    @DisplayName("이미 보낸 회원가입 요청과 중복되는 정보로 요청 시 예외가 잘 발생하는지")
    @Test
    void sendSignUpRequest_FailureDataIntegrityViolation() {
        AdminSignupRequest request = new AdminSignupRequest("email@example.com", "nickname");

        when(adminRepository.existsByEmailOrNickname(request.getEmail(), request.getNickname())).thenReturn(false);
        doThrow(new DataIntegrityViolationException("")).when(adminSignUpRepository).save(any());

        assertThrows(BadRequestException.class, () -> adminService.sendSignUpRequest(request));
    }

    @DisplayName("유효한 관리자 이메일로 정보 조회가 잘 동작하는지")
    @Test
    void whenGetAdminInfoByEmail_withValidAdminEmail_thenSuccess() {
        String email = "test@test.com";
        Admin admin = new Admin(email, "password", "스톤", null, "profile.jpg");
        when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));

        AdminInfoResponse adminInfoResponse = adminService.getAdminInfoByEmail(email);

        assertThat(adminInfoResponse.getUsername()).isEqualTo("스톤");
        verify(adminRepository).findByEmail(email);
    }

    @DisplayName("유효하지 않은 관리자 이메일로 정보 조회 시 예외가 잘 발생하는지")
    @Test
    void whenGetAdminInfoByEmail_withInvalidEmail_thenThrowException() {
        String wrongEmail = "nonexistent@example.com";
        when(adminRepository.findByEmail(wrongEmail)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.getAdminInfoByEmail(wrongEmail))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("관리자 정보를 찾을 수 없습니다.");

        verify(adminRepository).findByEmail(wrongEmail);
    }

    @DisplayName("getSignUpRequestList 메서드가 페이지 응답을 잘 보내는지")
    @Test
    void getSignUpRequstList_ReturnsPageOfAdminSignUpListResponse() {
        AdminSignUp adminSignUp1 = new AdminSignUp("email1@example.com", "nickname1");
        AdminSignUp adminSignUp2 = new AdminSignUp("email2@example.com", "nickname2");
        List<AdminSignUp> adminSignUps = Arrays.asList(adminSignUp1, adminSignUp2);
        Page<AdminSignUp> pageOfAdminSignUps = new PageImpl<>(adminSignUps);

        when(adminSignUpRepository.findAll(any(Pageable.class))).thenReturn(pageOfAdminSignUps);

        AdminSignUpListRequest request = new AdminSignUpListRequest(1, 10);
        Page<AdminSignUpListResponse> response = adminService.getSignUpRequestList(request);

        assertEquals(2, response.getContent().size());
        assertEquals("email1@example.com", response.getContent().get(0).getEmail());
        assertEquals("nickname1", response.getContent().get(0).getNickname());
    }

}
