package kr.co.yigil.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    public boolean matches(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
