package kr.co.yigil.login.presentation;

import static kr.co.yigil.login.util.LoginUtils.extractToken;

import jakarta.servlet.http.HttpSession;
import kr.co.yigil.login.application.LoginStrategyManager;
import kr.co.yigil.login.application.strategy.LoginStrategy;
import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginStrategyManager loginStrategyManager;

    @PostMapping("/api/v1/login")
    public ResponseEntity<LoginResponse> login(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody LoginRequest loginRequest,
            HttpSession session
    ) {
        String accessToken = extractToken(authorizationHeader);
        LoginStrategy strategy = loginStrategyManager.getLoginStrategy(loginRequest.getProvider());
        LoginResponse response = strategy.login(loginRequest, accessToken, session);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/logout")
    public ResponseEntity<LoginResponse> logout(
            HttpSession session
    ) {
        session.invalidate();
        return ResponseEntity.ok(new LoginResponse("로그아웃 성공"));
    }
}
