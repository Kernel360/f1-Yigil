package kr.co.yigil.admin.infrastructure;

import kr.co.yigil.admin.domain.AdminSignUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSignUpRepository extends JpaRepository<AdminSignUp, Long> {

}
