package kr.co.yigil.login.application;

import jakarta.transaction.Transactional;
import kr.co.yigil.login.domain.OauthProvider;
import kr.co.yigil.login.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    public void login(final String providerName, final LoginRequest request) {

    }
}
