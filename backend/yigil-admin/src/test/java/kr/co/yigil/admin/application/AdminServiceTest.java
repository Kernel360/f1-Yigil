package kr.co.yigil.admin.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.repository.AdminRepository;
import kr.co.yigil.admin.domain.repository.AdminSignUpRepository;
import kr.co.yigil.admin.dto.request.AdminSingupRequest;
import kr.co.yigil.admin.dto.request.LoginRequest;
import kr.co.yigil.admin.dto.response.AdminInfoResponse;
import kr.co.yigil.admin.dto.response.AdminSignupResponse;
import kr.co.yigil.auth.application.JwtTokenProvider;
import kr.co.yigil.auth.dto.JwtToken;
import kr.co.yigil.global.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        AdminSingupRequest singupRequest = new AdminSingupRequest("user@example.com", "user");
        when(adminSignUpRepository.save(any(AdminSignUp.class))).thenReturn(null);

        AdminSignupResponse response = adminService.sendSignUpRequest(singupRequest);

        assertThat(response.getMessage()).isEqualTo("회원가입 요청이 완료되었습니다.");
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

}
