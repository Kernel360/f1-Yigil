package kr.co.yigil.admin.application;

import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.repository.AdminRepository;
import kr.co.yigil.admin.domain.repository.AdminSignUpRepository;
import kr.co.yigil.admin.dto.request.AdminSingupRequest;
import kr.co.yigil.admin.dto.request.LoginRequest;
import kr.co.yigil.admin.dto.response.AdminInfoResponse;
import kr.co.yigil.admin.dto.response.AdminSignupResponse;
import kr.co.yigil.auth.application.JwtTokenProvider;
import kr.co.yigil.auth.dto.JwtToken;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public JwtToken signIn(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional
    public AdminSignupResponse sendSignUpRequest(AdminSingupRequest request) {
        adminSignUpRepository.save(request.toEntity());
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

}
