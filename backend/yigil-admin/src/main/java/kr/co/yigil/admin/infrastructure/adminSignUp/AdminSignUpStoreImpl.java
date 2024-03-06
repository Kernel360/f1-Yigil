package kr.co.yigil.admin.infrastructure.adminSignUp;

import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.adminSignUp.AdminSignUpStore;
import kr.co.yigil.admin.infrastructure.AdminSignUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSignUpStoreImpl implements AdminSignUpStore {
    private final AdminSignUpRepository adminSignUpRepository;

    @Override
    public void store(AdminSignUp entity) {
        adminSignUpRepository.save(entity);
    }

    @Override
    public void remove(AdminSignUp signUp) {
        adminSignUpRepository.delete(signUp);
    }
}
