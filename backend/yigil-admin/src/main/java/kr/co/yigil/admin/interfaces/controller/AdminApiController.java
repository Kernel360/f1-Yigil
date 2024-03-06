package kr.co.yigil.admin.interfaces.controller;

import kr.co.yigil.admin.application.AdminFacade;
import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.admin.AdminCommand;
import kr.co.yigil.admin.domain.admin.AdminInfo;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpCommand.AdminSignUpRequest;
import kr.co.yigil.admin.interfaces.dto.mapper.AdminMapper;
import kr.co.yigil.admin.interfaces.dto.mapper.AdminSignupMapper;
import kr.co.yigil.admin.interfaces.dto.request.AdminPasswordUpdateRequest;
import kr.co.yigil.admin.interfaces.dto.request.AdminProfileImageUpdateRequest;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignupRequest;
import kr.co.yigil.admin.interfaces.dto.request.LoginRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpRejectRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminDetailInfoResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminPasswordUpdateResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminProfileImageUpdateResponse;
import kr.co.yigil.admin.interfaces.dto.response.AdminSignUpsResponse;
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
    private final AdminSignupMapper adminSignupMapper;
    private final AdminMapper adminMapper;

    @PostMapping("/signup")
    public ResponseEntity<AdminSignupResponse> sendSignUpRequest(@RequestBody AdminSignupRequest request) {

        AdminSignUpRequest command = adminSignupMapper.toCommand(request);
        adminFacade.sendSignUpRequest(command);

        return  ResponseEntity.ok(new AdminSignupResponse("회원가입 요청이 완료되었습니다."));
    }

    @GetMapping("/signup/list")
    public ResponseEntity<AdminSignUpsResponse> getSignUpRequestList(@ModelAttribute AdminSignUpListRequest request) {
        Page<AdminSignUp> adminSignUps = adminFacade.getSignUpRequestList(request);
        AdminSignUpsResponse response = adminSignupMapper.toResponse(adminSignUps);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup/accept")
    public ResponseEntity<SignUpAcceptResponse> acceptSignUp(@RequestBody SignUpAcceptRequest request) {
        adminFacade.acceptAdminSignUp(request);
        return ResponseEntity.ok(new SignUpAcceptResponse("가입 승인 완료"));
    }

    @PostMapping("/signup/reject")
    public ResponseEntity<SignUpRejectResponse> rejectSignUp(@RequestBody SignUpRejectRequest request) {
        adminFacade.rejectAdminSignUp(request);
        return ResponseEntity.ok(new SignUpRejectResponse("가입 거절 완료"));
    }

    @PostMapping("/login")
    public JwtToken login(@RequestBody LoginRequest request) throws Exception {
        AdminCommand.LoginRequest command = adminMapper.toCommand(request);
        return adminFacade.signIn(command);
    }

    @GetMapping("/info")
    public ResponseEntity<AdminInfoResponse> getMemberInfo(@AuthenticationPrincipal User user) {
        AdminInfo.AdminInfoResponse info = adminFacade.getAdminInfoByEmail(user.getUsername());
        AdminInfoResponse response = adminMapper.toResponse(info);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail-info")
    public ResponseEntity<AdminDetailInfoResponse> getMemberDetailInfo(@AuthenticationPrincipal User user) {
        AdminInfo.AdminDetailInfoResponse info = adminFacade.getAdminDetailInfoByEmail(user.getUsername());
        AdminDetailInfoResponse response = adminMapper.toResponse(info);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile-image")
    public ResponseEntity<AdminProfileImageUpdateResponse> updateProfileImage(@AuthenticationPrincipal User user, @ModelAttribute AdminProfileImageUpdateRequest request) {
        adminFacade.updateProfileImage(user.getUsername(), request.getProfileImageFile());
        return ResponseEntity.ok(new AdminProfileImageUpdateResponse("어드민 프로필 이미지 수정 완료"));
    }

    @PostMapping("/password")
    public ResponseEntity<AdminPasswordUpdateResponse> updatePassword(@AuthenticationPrincipal User user, @RequestBody AdminPasswordUpdateRequest request) {
        AdminCommand.AdminPasswordUpdateRequest command = adminMapper.toCommand(request);
        adminFacade.updatePassword(user.getUsername(), command);

        return ResponseEntity.ok(new AdminPasswordUpdateResponse("어드민 비밀번호 수정 완료"));
    }

    @PostMapping("/test")
    public ResponseEntity<String> testSignUp() {
        adminFacade.testSignUp();
        return ResponseEntity.ok("succeed test");
    }
}
