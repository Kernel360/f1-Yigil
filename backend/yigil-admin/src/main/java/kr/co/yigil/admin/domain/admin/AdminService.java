package kr.co.yigil.admin.domain.admin;

import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.admin.AdminCommand.AdminPasswordUpdateRequest;
import kr.co.yigil.admin.domain.admin.AdminCommand.LoginRequest;
import kr.co.yigil.admin.domain.admin.AdminInfo.AdminDetailInfoResponse;
import kr.co.yigil.auth.dto.JwtToken;
import org.springframework.web.multipart.MultipartFile;


public interface AdminService {

    JwtToken signIn(LoginRequest command);

    AdminInfo.AdminInfoResponse getAdminInfoByEmail(String email);

    AdminDetailInfoResponse getAdminDetailInfoByEmail(String email);

    void testSignUp();

    void updateProfileImage(String email, MultipartFile profileImageFile);

    void updatePassword(String email, AdminPasswordUpdateRequest command);

    Admin getAdmin(String username);

    Long getAdminId();
}
