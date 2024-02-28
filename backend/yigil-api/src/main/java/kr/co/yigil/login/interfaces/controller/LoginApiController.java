package kr.co.yigil.login.interfaces.controller;

import static kr.co.yigil.login.util.LoginUtils.extractToken;

import jakarta.servlet.http.HttpSession;
import kr.co.yigil.login.application.LoginFacade;
import kr.co.yigil.login.domain.LoginCommand;
import kr.co.yigil.login.interfaces.dto.mapper.LoginMapper;
import kr.co.yigil.login.interfaces.dto.request.LoginRequest;
import kr.co.yigil.login.interfaces.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginApiController {

    private final LoginFacade loginFacade;
    private final LoginMapper loginMapper;

    @PostMapping("/api/v1/login")
    public ResponseEntity<LoginResponse> login(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody LoginRequest loginRequest,
            HttpSession session
    ) {
        String accessToken = extractToken(authorizationHeader);
        LoginCommand.LoginRequest loginCommand = loginMapper.toCommandLoginRequest(loginRequest);
        Long loginMemberId = loginFacade.executeLoginStrategy(loginCommand, accessToken);
        session.setAttribute("memberId", loginMemberId);

        return ResponseEntity.ok(new LoginResponse("로그인 성공"));
    }

    @GetMapping("/api/v1/logout")
    public ResponseEntity<LoginResponse> logout(
            HttpSession session
    ) {
        session.invalidate();
        return ResponseEntity.ok(new LoginResponse("로그아웃 성공"));
    }
}
