package kr.co.yigil.admin.interfaces.controller;

import kr.co.yigil.admin.application.AdminFacade;
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
import kr.co.yigil.auth.dto.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminApiController {

    private final AdminFacade adminFacade;

    @PostMapping("/signup")
    public ResponseEntity<AdminSignupResponse> sendSignUpRequest(@RequestBody AdminSignupRequest request) {
        adminFacade.sendSignUpRequest(request);

        return  ResponseEntity.ok(new AdminSignupResponse("회원가입 요청이 완료되었습니다."));
    }

    @GetMapping("/signup/list")
    public ResponseEntity<Page<AdminSignUpListResponse>> getSignUpRequestList(@ModelAttribute AdminSignUpListRequest request) {
        Page<AdminSignUpListResponse> response = adminFacade.getSignUpRequestList(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup/accept")
    public ResponseEntity<SignUpAcceptResponse> acceptSignUp(@RequestBody SignUpAcceptRequest request) {
        adminFacade.acceptAdminSignUp(request);
        return ResponseEntity.ok(new SignUpAcceptResponse("가입 승인 완료");
);
    }

    @PostMapping("/signup/reject")
    public ResponseEntity<SignUpRejectResponse> rejectSignUp(@RequestBody SignUpRejectRequest request) {
        adminFacade.rejectAdminSignUp(request);
        return ResponseEntity.ok(new SignUpRejectResponse("가입 거절 완료"));
    }

    @PostMapping("/login")
    public JwtToken login(@RequestBody LoginRequest request) throws Exception {
        return adminFacade.signIn(request);
    }

    @GetMapping("/info")
    public ResponseEntity<AdminInfoResponse> getMemberInfo(@AuthenticationPrincipal User user) {
        AdminInfoResponse response = adminFacade.getAdminInfoByEmail(user.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test")
    public ResponseEntity<String> testSignUp() {
        adminFacade.testSignUp();
        return ResponseEntity.ok("succeed test");
    }
}
