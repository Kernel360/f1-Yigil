package kr.co.yigil.login.presentation;

import jakarta.servlet.http.HttpSession;
import kr.co.yigil.login.application.LoginStrategyManager;
import kr.co.yigil.login.application.strategy.LoginStrategy;
import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginStrategyManager loginStrategyManager;

    @PostMapping("/api/v1/{provider}")
    public ResponseEntity<LoginResponse> login(
            @PathVariable final String provider,
            @RequestBody final LoginRequest loginRequest,
            HttpSession session
    ) {
        LoginStrategy strategy = loginStrategyManager.getLoginStrategy(provider);
        LoginResponse response = strategy.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
