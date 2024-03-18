package kr.co.yigil.report.type.infrastructure;

import kr.co.yigil.report.type.domain.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Long>{

    boolean existsByName(String name);
}
