package kr.co.yigil.admin.domain.repository;

import java.util.Optional;
import kr.co.yigil.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}
