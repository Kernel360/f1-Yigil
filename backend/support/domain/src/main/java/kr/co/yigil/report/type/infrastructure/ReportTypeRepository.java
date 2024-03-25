package kr.co.yigil.report.type.infrastructure;

import java.util.List;
import kr.co.yigil.report.type.domain.ReportType;
import kr.co.yigil.report.type.domain.ReportTypeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Long>{
    List<ReportType> findAllByStatus(ReportTypeStatus status);
    boolean existsByName(String name);
}
