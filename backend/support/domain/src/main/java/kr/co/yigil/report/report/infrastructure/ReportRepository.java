package kr.co.yigil.report.report.infrastructure;

import kr.co.yigil.report.report.domain.Report;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{
    Page<Report> findAllByReportTypeId(@NotNull Long typeId, PageRequest pageRequest);

    void deleteByIdIn(List<Long> ids);
    void deleteById(@NotNull Long id);
}
