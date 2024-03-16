package kr.co.yigil.report.report.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportReader {

    Page<Report> getReportsByType(Long typeId, int page, int size);
    Report getReportById(Long id);

    Page<Report> getAllReports(Pageable pageable, Long memberId, Long typeId, String keyword);
}
