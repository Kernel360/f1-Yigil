package kr.co.yigil.login.domain;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.co.yigil.login.infrastructure.LoginStrategy;
import org.springframework.stereotype.Service;

@Service
public class LoginStrategyManager {
    private final Map<String, LoginStrategy> loginStrategyMap;

    public LoginStrategyManager(List<LoginStrategy> strategies) {
        loginStrategyMap = strategies.stream()
                .collect(Collectors.toMap(LoginStrategy::getProviderName,
                        Function.identity()));
    }

    public LoginStrategy getLoginStrategy(String provider) {
        return loginStrategyMap.get(provider);
    }
}
