package kr.co.yigil.admin.domain.admin;

import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_PASSWORD_DOES_NOT_MATCH;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.admin.AdminCommand.AdminPasswordUpdateRequest;
import kr.co.yigil.admin.domain.admin.AdminCommand.LoginRequest;
import kr.co.yigil.admin.domain.admin.AdminInfo.AdminDetailInfoResponse;
import kr.co.yigil.auth.application.JwtTokenProvider;
import kr.co.yigil.auth.dto.JwtToken;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.domain.FileUploader;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminReader adminReader;
    private final AdminStore adminStore;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final FileUploader fileUploader;

    @Override
    @Transactional(readOnly = true)
    public JwtToken signIn(LoginRequest command) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                command.getEmail(), command.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminInfo.AdminInfoResponse getAdminInfoByEmail(String email) {
        Admin admin = adminReader.getAdminByEmail(email);
        return new AdminInfo.AdminInfoResponse(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDetailInfoResponse getAdminDetailInfoByEmail(String email) {
        Admin admin = adminReader.getAdminByEmail(email);
        return new AdminDetailInfoResponse(admin);
    }

    @Override
    @Transactional
    public void updateProfileImage(String email, MultipartFile profileImageFile) {
        Admin admin = adminReader.getAdminByEmail(email);
        AttachFile updatedProfile = fileUploader.upload(profileImageFile);

        admin.updateProfileImage(updatedProfile);
    }

    @Override
    @Transactional
    public void updatePassword(String email, AdminPasswordUpdateRequest command) {
        Admin admin = adminReader.getAdminByEmail(email);

        if (!passwordEncoder.matches(command.getExistingPassword(), admin.getPassword())) {
            throw new AuthException(ADMIN_PASSWORD_DOES_NOT_MATCH);
        }

        String encodedPassword = passwordEncoder.encode(command.getNewPassword());
        admin.updatePassword(encodedPassword);

    }

    @Override
    public Admin getAdmin(String username) {
        return adminReader.getAdminByEmail(username);
    }

    @Override
    public Long getAdminId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            String userEmail = userDetails.getUsername();
            Admin admin = getAdmin(userEmail);
            return admin.getId();
        }

        throw new BadRequestException(ExceptionCode.ADMIN_NOT_FOUND);
    }

    @Override
    @Transactional
    public void testSignUp() {
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        Admin admin = new Admin("kiit7@naver.com",
                passwordEncoder.encode("0000"),
                "스톤",
                roles);

        adminStore.store(admin);
    }
}
