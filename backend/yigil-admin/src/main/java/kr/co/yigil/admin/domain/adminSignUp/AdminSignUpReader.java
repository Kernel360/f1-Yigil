package kr.co.yigil.admin.domain.adminSignUp;

import kr.co.yigil.admin.domain.AdminSignUp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminSignUpReader {

    Page<AdminSignUp> findAll(Pageable pageable);

    AdminSignUp findById(Long id);
}
