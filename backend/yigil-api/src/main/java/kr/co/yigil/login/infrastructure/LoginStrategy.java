package kr.co.yigil.login.infrastructure;

import kr.co.yigil.login.domain.LoginCommand;

public interface LoginStrategy {
    Long processLogin(LoginCommand.LoginRequest loginCommand, String accessToken);

    String getProviderName();

}
