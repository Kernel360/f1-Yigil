package kr.co.yigil.admin.domain.adminSignUp;

import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignupRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpRejectRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignUpListResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignupResponse;
import kr.co.yigil.admin.interfaces.dto.response.SignUpAcceptResponse;
import kr.co.yigil.admin.interfaces.dto.response.SignUpRejectResponse;
import org.springframework.data.domain.Page;

public interface AdminSignUpService {

    void  sendSignUpRequest(AdminSignupRequest request);

    Page<AdminSignUpListResponse> getSignUpRequestList(AdminSignUpListRequest request);

    void acceptAdminSignUp(SignUpAcceptRequest request);

    void rejectAdminSignUp(SignUpRejectRequest request);
}
