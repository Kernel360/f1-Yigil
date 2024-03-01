package kr.co.yigil.admin.domain.admin;

import kr.co.yigil.admin.domain.admin.AdminCommand.LoginRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import kr.co.yigil.auth.dto.JwtToken;


public interface AdminService {

    JwtToken signIn(LoginRequest command);

    AdminInfo.AdminInfoResponse getAdminInfoByEmail(String email);

    void testSignUp();
}
