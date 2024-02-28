package kr.co.yigil.admin.domain.admin;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.admin.interfaces.dto.request.LoginRequest;
import kr.co.yigil.admin.interfaces.dto.response.AdminInfoResponse;
import kr.co.yigil.auth.application.JwtTokenProvider;
import kr.co.yigil.auth.dto.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminReader adminReader;
    private final AdminStore adminStore;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public JwtToken signIn(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminInfoResponse getAdminInfoByEmail(String email) {
        Admin admin = adminReader.getAdminByEmail(email);
        return AdminInfoResponse.from(admin);
    }

    @Override
    @Transactional
    public void testSignUp() {
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        Admin admin = new Admin("kiit7@naver.com",
                passwordEncoder.encode("0000"),
                "스톤",
                roles,
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fpixabay.com%2Fko%2Fimages%2Fsearch%2F%25ED%2594%2584%25EB%25A1%259C%25ED%2595%2584%2F&psig=AOvVaw0bBAscVMby6pWvg2XGqdjW&ust=1706775743831000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCKCe6KCZh4QDFQAAAAAdAAAAABAE");

        adminStore.store(admin);
    }

}
