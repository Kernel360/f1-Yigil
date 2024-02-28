package kr.co.yigil.admin.application;

import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpService;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignupRequest;
import kr.co.yigil.admin.interfaces.dto.request.LoginRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpRejectRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignUpListResponse;
import kr.co.yigil.auth.dto.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminFacade {

    private final AdminService adminService;
    private final AdminSignUpService adminSignUpService;

    public void sendSignUpRequest(AdminSignupRequest request) {
        adminSignUpService.sendSignUpRequest(request);
    }

    public Page<AdminSignUpListResponse> getSignUpRequestList(AdminSignUpListRequest request) {
        return adminSignUpService.getSignUpRequestList(request);
    }

    public void acceptAdminSignUp(SignUpAcceptRequest request) {
        adminSignUpService.acceptAdminSignUp(request);
    }

    public void rejectAdminSignUp(SignUpRejectRequest request) {
        adminSignUpService.rejectAdminSignUp(request);
    }

    public JwtToken signIn(LoginRequest request) throws Exception {
        return adminService.signIn(request);
    }

    public AdminInfoResponse getAdminInfoByEmail(String email) {
        return adminService.getAdminInfoByEmail(email);
    }

    public void testSignUp() {
        adminService.testSignUp();
    }
}
