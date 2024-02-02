package kr.co.yigil.auth.application;

import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.repository.AdminRepository;
import kr.co.yigil.global.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AdminRepository adminRepository;
    private final PasswordService passwordService;

    @Override
    public Authentication authenticate(Authentication authentication) {
       String username = authentication.getName();
       String password = authentication.getCredentials().toString();

       Admin admin = adminRepository.findByEmail(username)
               .orElseThrow(() -> new AuthException(ADMIN_NOT_FOUND));

       if (!passwordService.matches(password, admin.getPassword())) {
           throw new BadCredentialsException("Password does not match");
       }

        List<GrantedAuthority> authorities = admin.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

       return new UsernamePasswordAuthenticationToken(admin, null, authorities );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
