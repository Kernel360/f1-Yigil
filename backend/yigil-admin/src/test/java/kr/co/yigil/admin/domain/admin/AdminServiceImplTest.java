package kr.co.yigil.admin.domain.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.admin.domain.admin.AdminCommand.LoginRequest;
import kr.co.yigil.auth.application.JwtTokenProvider;
import kr.co.yigil.auth.dto.JwtToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private AdminReader adminReader;
    @Mock
    private AdminStore adminStore;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    @DisplayName("signIn 메서드가 JwtToken을 잘 반환하는지")
    @Test
    void signIn_ShouldReturnJwtToken() {
        AdminCommand.LoginRequest command = new LoginRequest("test@test.com", "password");
        Authentication authentication = mock(Authentication.class);
        JwtToken expectedToken = new JwtToken("mockType", "mockAccessToken", "mockRefreshToken");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(expectedToken);

        JwtToken result = adminService.signIn(command);

        assertEquals(expectedToken, result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).generateToken(authentication);
    }

    @DisplayName("getAdminInfoByEmail 메서드가 AdminInfoResponse를 잘 반환하는지")
    @Test
    void getAdminInfoByEmail_ShouldReturnAdminInfoResponse() {
        String email = "test@test.com";
        Admin admin = mock(Admin.class);
        AdminInfo.AdminInfoResponse expectedResponse = new AdminInfo.AdminInfoResponse(admin);

        when(adminReader.getAdminByEmail(email)).thenReturn(admin);

        AdminInfo.AdminInfoResponse result = adminService.getAdminInfoByEmail(email);

        assertEquals(expectedResponse.getUsername(), result.getUsername());
        verify(adminReader).getAdminByEmail(email);
    }

    @Test
    void testSignUp_ShouldStoreAdmin() {
        Admin admin = mock(Admin.class);
        when(passwordEncoder.encode("0000")).thenReturn("encodedPassword");

        adminService.testSignUp();

        verify(adminStore).store(any(Admin.class));
    }
}