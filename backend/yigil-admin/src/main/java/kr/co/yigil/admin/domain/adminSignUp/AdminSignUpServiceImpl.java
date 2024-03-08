package kr.co.yigil.admin.domain.adminSignUp;

import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_ALREADY_EXISTED;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.admin.AdminReader;
import kr.co.yigil.admin.domain.admin.AdminStore;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpCommand.AdminSignUpRequest;
import kr.co.yigil.admin.infrastructure.adminSignUp.AdminPasswordGenerator;
import kr.co.yigil.admin.interfaces.dto.request.AdminSignUpListRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpAcceptRequest;
import kr.co.yigil.admin.interfaces.dto.request.SignUpRejectRequest;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSignUpServiceImpl implements AdminSignUpService {


    private final AdminSignUpReader adminSignUpReader;
    private final AdminSignUpStore adminSignUpStore;
    private final EmailSender emailSender;

    private final AdminReader adminReader;

    private final AdminPasswordGenerator adminPasswordGenerator;
    private final PasswordEncoder passwordEncoder;

    private final AdminStore adminStore;

    @Override
    @Transactional
    public void sendSignUpRequest(AdminSignUpRequest command) {
        validateRequestAlreadySignedUp(command);
        try {
            adminSignUpStore.store(command.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(ADMIN_ALREADY_EXISTED);
        }
    }

    private void validateRequestAlreadySignedUp(AdminSignUpRequest command) {
        if (adminReader.existsByEmailOrNickname(command.getEmail(), command.getNickname())) {
            throw new BadRequestException(ADMIN_ALREADY_EXISTED);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminSignUp> getAdminSignUpList(AdminSignUpListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getDataCount());
        return adminSignUpReader.findAll(pageable);
    }

    @Override
    @Transactional
    public void acceptAdminSignUp(SignUpAcceptRequest request) {
        List<Long> acceptedAdminIds = request.getIds();
        signUpNewAdmins(acceptedAdminIds);
    }

    private void signUpNewAdmins(List<Long> ids) {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        for (Long id : ids) {
            signUpNewAdmin(id, roles);
        }
    }

    private void signUpNewAdmin(Long id, List<String> roles) {
        AdminSignUp signUp = adminSignUpReader.findById(id);

        String temporaryPassword = adminPasswordGenerator.generateRandomPassword();

        Admin admin = new Admin(signUp.getEmail(),
                passwordEncoder.encode(temporaryPassword),
                signUp.getNickname(),
                roles);

        adminStore.store(admin);
        emailSender.sendAcceptEmail(signUp, temporaryPassword);
        adminSignUpStore.remove(signUp);
    }

    private void deleteAdminSignUpRequest(Long id) {
        AdminSignUp signUp = adminSignUpReader.findById(id);

        emailSender.sendRejectEmail(signUp);
        adminSignUpStore.remove(signUp);
    }

    private void deleteAdminSignUpRequests(List<Long> ids) {
        for (Long id : ids) {
            deleteAdminSignUpRequest(id);
        }
    }

    @Override
    @Transactional
    public void rejectAdminSignUp(SignUpRejectRequest request) {
        List<Long> rejectedAdminIds = request.getIds();
        deleteAdminSignUpRequests(rejectedAdminIds);
    }

}
