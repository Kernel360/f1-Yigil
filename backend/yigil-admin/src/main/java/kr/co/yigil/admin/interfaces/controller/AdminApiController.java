package kr.co.yigil.admin.interfaces.controller;

import kr.co.yigil.admin.domain.AdminService;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.AdminSingupRequest;
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
@RequestMapping("/admin/api/v1/admins")
public class AdminApiController {

    private final AdminService adminService;

    @PostMapping("/login")
    public JwtToken login(@RequestBody LoginRequest request) throws Exception {
        return adminService.signIn(request);
    }

    @PostMapping("/signup")
    public ResponseEntity<AdminSignupResponse> sendSignUpRequest(@RequestBody AdminSingupRequest request) {
        AdminSignupResponse response = adminService.sendSignUpRequest(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/signup/list")
    public ResponseEntity<Page<AdminSignUpListResponse>> getSignUpRequestList(@ModelAttribute AdminSignUpListRequest request) {
        Page<AdminSignUpListResponse> response = adminService.getSignUpRequestList(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup/accept")
    public ResponseEntity<SignUpAcceptResponse> acceptSignUp(@RequestBody SignUpAcceptRequest request) {
        SignUpAcceptResponse response = adminService.acceptAdminSignUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup/reject")
    public ResponseEntity<SignUpRejectResponse> rejectSignUp(@RequestBody SignUpRejectRequest request) {
        SignUpRejectResponse response = adminService.rejectAdminSignUp(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<AdminInfoResponse> getMemberInfo(@AuthenticationPrincipal User user) {
        AdminInfoResponse response = adminService.getAdminInfoByEmail(user.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test")
    public ResponseEntity<String> testSignUp() {
        adminService.testSignUp();
        return ResponseEntity.ok("succeed test");
    }
}
