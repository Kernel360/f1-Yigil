package kr.co.yigil.admin.application;

import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_ALREADY_EXISTED;
import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_NOT_FOUND;
import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_SIGNUP_REQUEST_NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.repository.AdminRepository;
import kr.co.yigil.admin.domain.repository.AdminSignUpRepository;
import kr.co.yigil.admin.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.dto.request.AdminSingupRequest;
import kr.co.yigil.admin.dto.request.LoginRequest;
import kr.co.yigil.admin.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.dto.request.SignUpRejectRequest;
import kr.co.yigil.admin.dto.response.AdminInfoResponse;
import kr.co.yigil.admin.dto.response.AdminSignUpListResponse;
import kr.co.yigil.admin.dto.response.AdminSignupResponse;
import kr.co.yigil.admin.dto.response.SignUpAcceptResponse;
import kr.co.yigil.admin.dto.response.SignUpRejectResponse;
import kr.co.yigil.auth.application.JwtTokenProvider;
import kr.co.yigil.auth.dto.JwtToken;
import kr.co.yigil.email.EmailEventType;
import kr.co.yigil.email.EmailSendEvent;
import kr.co.yigil.email.EmailSendEventListener;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminSignUpRepository adminSignUpRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminPasswordGenerator adminPasswordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public JwtToken signIn(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional
    public AdminSignupResponse sendSignUpRequest(AdminSingupRequest request) {
        validateRequestAlreadySignedUp(request);
        try {
            adminSignUpRepository.save(request.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(ADMIN_ALREADY_EXISTED);
        }

        return new AdminSignupResponse("회원가입 요청이 완료되었습니다.");
    }

    @Transactional(readOnly = true)
    public AdminInfoResponse getAdminInfoByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email).orElseThrow(() ->
                new BadRequestException(ADMIN_NOT_FOUND));
        return AdminInfoResponse.from(admin);
    }

    @Transactional
    public void testSignUp() {
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        Admin admin = new Admin("kiit7@naver.com",
                passwordEncoder.encode("0000"),
                "스톤",
                roles,
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fpixabay.com%2Fko%2Fimages%2Fsearch%2F%25ED%2594%2584%25EB%25A1%259C%25ED%2595%2584%2F&psig=AOvVaw0bBAscVMby6pWvg2XGqdjW&ust=1706775743831000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCKCe6KCZh4QDFQAAAAAdAAAAABAE");

        adminRepository.save(admin);
    }

    private void validateRequestAlreadySignedUp(AdminSingupRequest request) {
        if(adminRepository.existsByEmailOrNickname(request.getEmail(), request.getNickname())) {
            throw new BadRequestException(ADMIN_ALREADY_EXISTED);
        }
    }

    @Transactional(readOnly = true)
    public Page<AdminSignUpListResponse> getSignUpRequestList(AdminSignUpListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getDataCount());
        Page<AdminSignUp> adminSignUps = adminSignUpRepository.findAll(pageable);

        return convertAdminSignUpPageToResponse(adminSignUps);
    }

    private Page<AdminSignUpListResponse> convertAdminSignUpPageToResponse(Page<AdminSignUp> adminSignUps) {
        return adminSignUps.map(adminSignUp -> new AdminSignUpListResponse(
                adminSignUp.getId(),
                adminSignUp.getEmail(),
                adminSignUp.getNickname(),
                adminSignUp.getCreatedAt()
        ));
    }

    @Transactional
    public SignUpAcceptResponse acceptAdminSignUp(SignUpAcceptRequest request) {
        List<String> acceptedAdminIds = request.getIds();
        signUpNewAdmins(acceptedAdminIds);
        return new SignUpAcceptResponse("가입 승인 완료");
    }

    private void signUpNewAdmins(List<String> ids) {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        for (String id: ids) {
            signUpNewAdmin(Long.parseLong(id), roles);
        }
    }

    private void signUpNewAdmin(Long id, List<String> roles) {
        AdminSignUp signUp = adminSignUpRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ADMIN_SIGNUP_REQUEST_NOT_FOUND));

        String temporaryPassword = adminPasswordGenerator.generateRandomPassword();

        Admin admin = new Admin(signUp.getEmail(),
                passwordEncoder.encode(temporaryPassword),
                signUp.getNickname(),
                roles,
                "http://cdn.yigil.co.kr/images/0a1d6eaf-24ad-4c2a-b383-15eac96daec0_%E1%84%90%E1%85%A9%E1%84%81%E1%85%B5.jpeg");

        adminRepository.save(admin);
        sendAcceptEmail(signUp, temporaryPassword);
        adminSignUpRepository.delete(signUp);
    }

    private void sendAcceptEmail(AdminSignUp signUp, String password) {
        EmailSendEvent event = new EmailSendEvent(this, signUp.getEmail(), "[이길로그] 관리자 서비스 가입이 완료되었습니다.", "", password,
                EmailEventType.ADMIN_SIGN_UP_ACCEPT);
        eventPublisher.publishEvent(event);
    }

    @Transactional
    public SignUpRejectResponse rejectAdminSignUp(SignUpRejectRequest request) {
        List<String> rejectedAdminIds = request.getIds();
        deleteAdminSignUpRequests(rejectedAdminIds);
        return new SignUpRejectResponse("가입 거절 완료");
    }

    private void deleteAdminSignUpRequests(List<String> ids) {
        for(String id: ids) {
            deleteAdminSignUpRequest(Long.parseLong(id));
        }
    }

    private void deleteAdminSignUpRequest(Long id) {
        AdminSignUp signUp = adminSignUpRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ADMIN_SIGNUP_REQUEST_NOT_FOUND));

        sendRejectEmail(signUp);
        adminSignUpRepository.delete(signUp);
    }

    private void sendRejectEmail(AdminSignUp signUp) {
        EmailSendEvent event = new EmailSendEvent(this, signUp.getEmail(), "[이길로그] 관리자 서비스 가입이 거절되었습니다.", "", "", EmailEventType.ADMIN_SIGN_UP_REJECT);
        eventPublisher.publishEvent(event);
    }
}
