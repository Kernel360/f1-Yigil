package kr.co.yigil.admin.infrastructure.admin;

import kr.co.yigil.admin.domain.admin.Admin;
import kr.co.yigil.admin.domain.admin.AdminStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminStoreImpl implements AdminStore {
    private final AdminRepository adminRepository;

    @Override
    public void store(Admin admin) {
        adminRepository.save(admin);
    }
}
