package kr.co.yigil.admin.infrastructure.admin;

import static kr.co.yigil.global.exception.ExceptionCode.ADMIN_NOT_FOUND;

import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.admin.AdminReader;
import kr.co.yigil.admin.infrastructure.AdminRepository;
import kr.co.yigil.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminReaderImpl implements AdminReader {

    private final AdminRepository adminRepository;

    @Override
    public boolean existsByEmailOrNickname(String email, String nickname) {
        return adminRepository.existsByEmailOrNickname(email, nickname);
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(() ->
                new BadRequestException(ADMIN_NOT_FOUND));
    }
}
