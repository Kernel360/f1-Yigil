package kr.co.yigil.admin.domain.adminSignUp;

import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpCommand.AdminSignUpRequest;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpRejectRequest;
import org.springframework.data.domain.Page;

public interface AdminSignUpService {

    void  sendSignUpRequest(AdminSignUpRequest command);

    Page<AdminSignUp> getAdminSignUpList(AdminSignUpListRequest request);

    void acceptAdminSignUp(SignUpAcceptRequest request);

    void rejectAdminSignUp(SignUpRejectRequest request);
}
