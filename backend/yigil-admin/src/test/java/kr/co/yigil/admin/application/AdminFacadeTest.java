package kr.co.yigil.admin.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import kr.co.yigil.admin.domain.admin.AdminCommand;
import kr.co.yigil.admin.domain.admin.AdminCommand.AdminUpdateRequest;
import kr.co.yigil.admin.domain.admin.AdminCommand.LoginRequest;
import kr.co.yigil.admin.domain.admin.AdminInfo.AdminDetailInfoResponse;
import kr.co.yigil.admin.domain.admin.AdminInfo.AdminInfoResponse;
import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUp;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpCommand.AdminSignUpRequest;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpService;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpRejectRequest;
import kr.co.yigil.auth.dto.JwtToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
public class AdminFacadeTest {

    @Mock
    private AdminService adminService;

    @Mock
    private AdminSignUpService adminSignUpService;

    @InjectMocks
    private AdminFacade adminFacade;

    @DisplayName("sendSignUpRequest 메서드가 AdminSignUpService를 잘 호출하는지")
    @Test
    void sendSignUpRequest_ShouldCallService() {
        AdminSignUpRequest command = mock(AdminSignUpRequest.class);

        doNothing().when(adminSignUpService).sendSignUpRequest(command);

        adminFacade.sendSignUpRequest(command);

        verify(adminSignUpService).sendSignUpRequest(command);
    }

    @DisplayName("getSignUpRequestList 메서드가 Page<AdminSignUp>를 잘 반환하는지")
    @Test
    void getSignUpRequestList_ShouldReturnPage() {
        AdminSignUpListRequest request = mock(AdminSignUpListRequest.class);
        Page<AdminSignUp> expectedPage = new PageImpl<>(Collections.emptyList());

        when(adminSignUpService.getAdminSignUpList(request)).thenReturn(expectedPage);

        Page<AdminSignUp> result = adminFacade.getSignUpRequestList(request);

        assertEquals(expectedPage, result);
        verify(adminSignUpService).getAdminSignUpList(request);
    }

    @DisplayName("acceptAdminSignUp 메서드가 AdminSignUpService를 잘 호출하는지")
    @Test
    void acceptAdminSignUp_ShouldCallService() {
        SignUpAcceptRequest request = mock(SignUpAcceptRequest.class);

        doNothing().when(adminSignUpService).acceptAdminSignUp(request);

        adminFacade.acceptAdminSignUp(request);

        verify(adminSignUpService).acceptAdminSignUp(request);
    }

    @DisplayName("rejectAdminSignUp 메서드가 AdminSignUpService를 잘 호출하는지")
    @Test
    void rejectAdminSignUp_ShouldCallService() {
        SignUpRejectRequest request = mock(SignUpRejectRequest.class);

        doNothing().when(adminSignUpService).rejectAdminSignUp(request);

        adminFacade.rejectAdminSignUp(request);

        verify(adminSignUpService).rejectAdminSignUp(request);
    }

    @DisplayName("signIn 메서드가 JwtToken을 잘 반환하는지")
    @Test
    void signIn_ShouldReturnJwtToken() throws Exception {
        LoginRequest command = mock(LoginRequest.class);
        JwtToken expectedToken = new JwtToken("mockType", "mockAccessToken", "mockRefreshToken");

        when(adminService.signIn(command)).thenReturn(expectedToken);

        JwtToken result = adminFacade.signIn(command);

        assertEquals(expectedToken, result);
        verify(adminService).signIn(command);
    }

    @DisplayName("getAdminInfoByEmail 메서드가 AdminInfoResponse를 잘 반환하는지")
    @Test
    void getAdminInfoByEmail_ShouldReturnAdminInfoResponse() {
        String email = "test@test.com";
        AdminInfoResponse expectedResponse = mock(AdminInfoResponse.class);

        when(adminService.getAdminInfoByEmail(email)).thenReturn(expectedResponse);

        AdminInfoResponse result = adminFacade.getAdminInfoByEmail(email);

        assertEquals(expectedResponse, result);
        verify(adminService).getAdminInfoByEmail(email);
    }

    @DisplayName("getAdminDetailInfoByEmail 메서드가 AdminDetailInfoResponse를 잘 반환하는지")
    @Test
    void getAdminDetailInfoByEmail_ShouldReturnAdminDetailInfoResponse() {
        String email = "test@test.com";
        AdminDetailInfoResponse expectedResponse = mock(AdminDetailInfoResponse.class);

        when(adminService.getAdminDetailInfoByEmail(email)).thenReturn(expectedResponse);

        AdminDetailInfoResponse result = adminFacade.getAdminDetailInfoByEmail(email);

        assertEquals(expectedResponse, result);
        verify(adminService).getAdminDetailInfoByEmail(email);
    }

    @DisplayName("updateAdminDetailInfo 메서드가 AdminService를 잘 호출하는지")
    @Test
    void updateAdminDetailInfo_ShouldCallService() {
        String email = "test@test.com";
        AdminUpdateRequest command = mock(AdminUpdateRequest.class);

        adminFacade.updateAdminDetailInfo(email, command);

        verify(adminService).updateAdminDetailInfo(email, command);
    }

        @DisplayName("testSignUp 메서드가 AdminService를 잘 호출하는지")
    @Test
    void testSignUp_ShouldCallService() {
        doNothing().when(adminService).testSignUp();

        adminFacade.testSignUp();

        verify(adminService).testSignUp();
    }
}