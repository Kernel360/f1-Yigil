package kr.co.yigil.admin.infrastructure.adminSignUp;

import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_SIGNUP_REQUEST_NOT_FOUND;

import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpReader;
import kr.co.yigil.admin.infrastructure.AdminSignUpRepository;
import kr.co.yigil.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSignUpReaderImpl implements AdminSignUpReader {
    private final AdminSignUpRepository adminSignUpRepository;
    @Override
    public Page<AdminSignUp> findAll(Pageable pageable) {
        return adminSignUpRepository.findAll(pageable);
    }

    @Override
    public AdminSignUp findById(Long id) {
        return adminSignUpRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ADMIN_SIGNUP_REQUEST_NOT_FOUND));
    }
}
