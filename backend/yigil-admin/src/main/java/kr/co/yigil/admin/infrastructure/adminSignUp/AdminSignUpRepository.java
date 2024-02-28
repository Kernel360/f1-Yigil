package kr.co.yigil.admin.infrastructure.adminSignUp;

import kr.co.yigil.admin.domain.adminSignUp.AdminSignUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSignUpRepository extends JpaRepository<AdminSignUp, Long> {

}
