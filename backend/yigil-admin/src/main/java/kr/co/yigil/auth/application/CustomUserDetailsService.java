package kr.co.yigil.auth.application;

import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_NOT_FOUND;

import kr.co.yigil.admin.domain.admin.Admin;
import kr.co.yigil.admin.infrastructure.admin.AdminRepository;
import kr.co.yigil.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new BadRequestException(ADMIN_NOT_FOUND));
    }

    private UserDetails createUserDetails(Admin admin) {
        return admin;
    }
}
