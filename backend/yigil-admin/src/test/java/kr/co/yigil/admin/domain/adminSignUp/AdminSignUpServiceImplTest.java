package kr.co.yigil.admin.domain.adminSignUp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import kr.co.yigil.admin.domain.admin.Admin;
import kr.co.yigil.admin.domain.admin.AdminReader;
import kr.co.yigil.admin.domain.admin.AdminStore;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpCommand.AdminSignUpRequest;
import kr.co.yigil.admin.infrastructure.adminSignUp.AdminPasswordGenerator;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpRejectRequest;
import kr.co.yigil.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AdminSignUpServiceImplTest {

    @Mock
    private AdminSignUpReader adminSignUpReader;
    @Mock
    private AdminSignUpStore adminSignUpStore;
    @Mock
    private EmailSender emailSender;
    @Mock
    private AdminReader adminReader;
    @Mock
    private AdminPasswordGenerator adminPasswordGenerator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AdminStore adminStore;

    @InjectMocks
    private AdminSignUpServiceImpl adminSignUpService;

    @DisplayName("sendSignUpRequest 메서드가 AdminSignUp을 잘 저장하는지")
    @Test
    void sendSignUpRequest_ShouldStoreAdminSignUp() {
        AdminSignUpCommand.AdminSignUpRequest command = new AdminSignUpRequest("email", "nickname");

        when(adminReader.existsByEmailOrNickname(anyString(), anyString())).thenReturn(false);
        adminSignUpService.sendSignUpRequest(command);

        verify(adminSignUpStore).store(any(AdminSignUp.class));
    }

    @DisplayName("sendSignUpRequest 메서드가 이미 가입된 이메일이나 닉네임이 있을 때 BadRequestException을 던지는지")
    @Test
    void sendSignUpRequest_ShouldThrowBadRequestException() {
        AdminSignUpCommand.AdminSignUpRequest command = new AdminSignUpRequest("email", "nickname");

        when(adminReader.existsByEmailOrNickname(anyString(), anyString())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> adminSignUpService.sendSignUpRequest(command));
    }

    @DisplayName("getAdminSignUpList 메서드가 AdminSignUp 리스트를 잘 반환하는지")
    @Test
    void getAdminSignUpList_ShouldReturnAdminSignUpList() {
        AdminSignUpListRequest request = new AdminSignUpListRequest(1, 10);
        Page<AdminSignUp> expectedPage = mock(Page.class);

        when(adminSignUpReader.findAll(any(PageRequest.class))).thenReturn(expectedPage);

        Page<AdminSignUp> result = adminSignUpService.getAdminSignUpList(request);

        assertEquals(expectedPage, result);
        verify(adminSignUpReader).findAll(any(PageRequest.class));
    }

    @DisplayName("acceptAdminSignUp 메서드가 AdminSignUp을 잘 수락하는지")
    @Test
    void acceptAdminSignUp_ShouldAcceptAdminSignUp() {
        SignUpAcceptRequest request = new SignUpAcceptRequest(List.of("1", "2"));

        when(adminPasswordGenerator.generateRandomPassword()).thenReturn("password");
        AdminSignUp adminSignUp1 = new AdminSignUp("email@email.com", "nickname");
        AdminSignUp adminSignUp2 = new AdminSignUp("email2@email.com", "nickname2");
        when(adminSignUpReader.findById(1L)).thenReturn(adminSignUp1);
        when(adminSignUpReader.findById(2L)).thenReturn(adminSignUp2);

        adminSignUpService.acceptAdminSignUp(request);

        verify(adminStore, times(request.getIds().size())).store(any(Admin.class));
        verify(emailSender, times(request.getIds().size())).sendAcceptEmail(any(AdminSignUp.class), anyString());
        verify(adminSignUpStore, times(request.getIds().size())).remove(any(AdminSignUp.class));
    }

    @DisplayName("rejectAdminSignUp 메서드가 AdminSignUp을 잘 거절하는지")
    @Test
    void rejectAdminSignUp_ShouldRejectAdminSignUp() {
        SignUpRejectRequest request = new SignUpRejectRequest(List.of("1", "2"));
        AdminSignUp adminSignUp1 = new AdminSignUp("email@email.com", "nickname");
        AdminSignUp adminSignUp2 = new AdminSignUp("email2@email.com", "nickname2");
        when(adminSignUpReader.findById(1L)).thenReturn(adminSignUp1);
        when(adminSignUpReader.findById(2L)).thenReturn(adminSignUp2);

        adminSignUpService.rejectAdminSignUp(request);

        verify(emailSender, times(request.getIds().size())).sendRejectEmail(any(AdminSignUp.class));
        verify(adminSignUpStore, times(request.getIds().size())).remove(any(AdminSignUp.class));
    }
}