package kr.co.yigil.admin.application;

import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.admin.AdminCommand.AdminPasswordUpdateRequest;
import kr.co.yigil.admin.domain.admin.AdminCommand.LoginRequest;
import kr.co.yigil.admin.domain.admin.AdminInfo.AdminDetailInfoResponse;
import kr.co.yigil.admin.domain.admin.AdminInfo.AdminInfoResponse;
import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpCommand.AdminSignUpRequest;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpService;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpRejectRequest;
import kr.co.yigil.auth.dto.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AdminFacade {

    private final AdminService adminService;
    private final AdminSignUpService adminSignUpService;

    public void sendSignUpRequest(AdminSignUpRequest command) {
        adminSignUpService.sendSignUpRequest(command);
    }

    public Page<AdminSignUp> getSignUpRequestList(AdminSignUpListRequest request) {
        return adminSignUpService.getAdminSignUpList(request);
    }

    public void acceptAdminSignUp(SignUpAcceptRequest request) {
        adminSignUpService.acceptAdminSignUp(request);
    }

    public void rejectAdminSignUp(SignUpRejectRequest request) {
        adminSignUpService.rejectAdminSignUp(request);
    }

    public JwtToken signIn(LoginRequest command) throws Exception {
        return adminService.signIn(command);
    }

    public AdminInfoResponse getAdminInfoByEmail(String email) {
        return adminService.getAdminInfoByEmail(email);
    }

    public AdminDetailInfoResponse getAdminDetailInfoByEmail(String email) {
        return adminService.getAdminDetailInfoByEmail(email);
    }

    public void testSignUp() {
        adminService.testSignUp();
    }

    public void updateProfileImage(String email, MultipartFile profileImageFile) {
        adminService.updateProfileImage(email, profileImageFile);
    }

    public void updatePassword(String email,  AdminPasswordUpdateRequest command) {
        adminService.updatePassword(email, command);
    }
}
