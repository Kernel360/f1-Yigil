package kr.co.yigil.admin.domain.admin;

import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_ALREADY_EXISTED;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.admin.domain.admin.Admin;
import kr.co.yigil.admin.domain.admin.AdminReader;
import kr.co.yigil.admin.domain.admin.AdminStore;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUp;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpReader;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpStore;
import kr.co.yigil.admin.infrastructure.AdminPasswordGenerator;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignupRequest;
import kr.co.yigil.admin.interfaces.dto.request.LoginRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpRejectRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignUpListResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignupResponse;
import kr.co.yigil.admin.interfaces.dto.response.SignUpAcceptResponse;
import kr.co.yigil.admin.interfaces.dto.response.SignUpRejectResponse;
import kr.co.yigil.auth.application.JwtTokenProvider;
import kr.co.yigil.auth.dto.JwtToken;
import kr.co.yigil.email.EmailEventType;
import kr.co.yigil.email.EmailSendEvent;
import kr.co.yigil.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface AdminService {

    JwtToken signIn(LoginRequest request);

    AdminInfoResponse getAdminInfoByEmail(String email);

    void testSignUp();
}
