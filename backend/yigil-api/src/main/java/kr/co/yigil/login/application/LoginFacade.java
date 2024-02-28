package kr.co.yigil.login.application;

import kr.co.yigil.login.domain.LoginCommand;
import kr.co.yigil.login.domain.LoginStrategyManager;
import kr.co.yigil.login.infrastructure.LoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginFacade {
    private final LoginStrategyManager loginStrategyManager;

    public Long executeLoginStrategy(LoginCommand.LoginRequest loginCommand, String accessToken) {
        LoginStrategy strategy = loginStrategyManager.getLoginStrategy(loginCommand.getProvider());
        return strategy.processLogin(loginCommand, accessToken);
    }
}
