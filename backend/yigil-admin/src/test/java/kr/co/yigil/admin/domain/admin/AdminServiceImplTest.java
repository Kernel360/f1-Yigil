package kr.co.yigil.admin.domain.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.admin.AdminCommand.LoginRequest;
import kr.co.yigil.auth.application.JwtTokenProvider;
import kr.co.yigil.auth.dto.JwtToken;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.domain.FileUploader;
import kr.co.yigil.global.exception.AuthException;
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
import org.springframework.web.multipart.MultipartFile;

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
    @Mock
    private FileUploader fileUploader;

    @InjectMocks
    private AdminServiceImpl adminService;

    @DisplayName("signIn 메서드가 JwtToken을 잘 반환하는지")
    @Test
    void signIn_ShouldReturnJwtToken() {
        AdminCommand.LoginRequest command = new LoginRequest("test@test.com", "password");
        Authentication authentication = mock(Authentication.class);
        JwtToken expectedToken = new JwtToken("mockType", "mockAccessToken", "mockRefreshToken");

        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
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
        String expectedName = "Test Admin";
        Admin admin = mock(Admin.class);

        when(admin.getNickname()).thenReturn(expectedName);
        when(adminReader.getAdminByEmail(email)).thenReturn(admin);

        AttachFile attachFile = mock(AttachFile.class);
        when(attachFile.getFileUrl()).thenReturn("http://test.com");
        when(admin.getProfileImage()).thenReturn(attachFile);

        AdminInfo.AdminInfoResponse result = adminService.getAdminInfoByEmail(email);

        assertEquals(expectedName, result.getNickname());

        verify(adminReader).getAdminByEmail(email);
    }

    @DisplayName("getAdminDetailInfoByEmail 메서드가 AdminDetailInfoResponse를 잘 반환하는지")
    @Test
    void getAdminDetailInfoByEmail_ShouldReturnAdminDetailInfoResponse() {
        String email = "test@test.com";
        String expectedName = "Test Admin";
        Admin admin = mock(Admin.class);

        when(admin.getNickname()).thenReturn(expectedName);
        when(adminReader.getAdminByEmail(email)).thenReturn(admin);

        AttachFile attachFile = mock(AttachFile.class);
        when(attachFile.getFileUrl()).thenReturn("http://test.com");
        when(admin.getProfileImage()).thenReturn(attachFile);

        AdminInfo.AdminDetailInfoResponse result = adminService.getAdminDetailInfoByEmail(email);

        assertEquals(expectedName, result.getNickname());

        verify(adminReader).getAdminByEmail(email);
    }

    @DisplayName("updateProfileImage 메서드가 Admin을 잘 업데이트하는지")
    @Test
    void updateProfileImage_ShouldUpdateAdmin() {
        Admin admin = mock(Admin.class);
        MultipartFile profileImageFile = mock(MultipartFile.class);
        AttachFile adminAttachFile = mock(AttachFile.class);
        when(adminReader.getAdminByEmail(anyString())).thenReturn(admin);
        when(fileUploader.upload(any())).thenReturn(adminAttachFile);

        adminService.updateProfileImage("email", profileImageFile);

        verify(admin).updateProfileImage(any());
    }

    @DisplayName("updatePassword 메서드의 파라미터로 올바른 existingPassword 입력시 Admin password를 잘 업데이트하는지")
    @Test
    void updatePassword_ShouldUpdateAdmin() {
        Admin admin = mock(Admin.class);
        AdminCommand.AdminPasswordUpdateRequest command = AdminCommand.AdminPasswordUpdateRequest.builder()
                .existingPassword("oldPassword")
                .newPassword("newPassword")
                .build();
        when(adminReader.getAdminByEmail(anyString())).thenReturn(admin);
        when(admin.getPassword()).thenReturn("existingencodedPassword");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        adminService.updatePassword("email", command);

        verify(admin).updatePassword(anyString());
    }

    @DisplayName("updatePassword 메서드가 기존 비밀번호가 틀렸을 때 예외를 잘 던지는지")
    @Test
    void updatePassword_ShouldThrowException_WhenExistingPasswordIsIncorrect() {
        Admin admin = mock(Admin.class);
        AdminCommand.AdminPasswordUpdateRequest command = AdminCommand.AdminPasswordUpdateRequest.builder()
                .existingPassword("wrongOldPassword")
                .newPassword("newPassword")
                .build();
        when(adminReader.getAdminByEmail(anyString())).thenReturn(admin);
        when(admin.getPassword()).thenReturn("existingencodedPassword");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(AuthException.class, () -> {
            adminService.updatePassword("email", command);
        });

        verify(admin, never()).updatePassword(anyString());
    }

    @Test
    void testSignUp_ShouldStoreAdmin() {
        Admin admin = mock(Admin.class);
        when(passwordEncoder.encode("0000")).thenReturn("encodedPassword");

        adminService.testSignUp();

        verify(adminStore).store(any(Admin.class));
    }
}