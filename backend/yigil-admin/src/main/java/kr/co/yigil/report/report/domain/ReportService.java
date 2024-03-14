package kr.co.yigil.report.report.domain;

import org.springframework.data.domain.Page;

public interface ReportService {

    Page<Report> getAllReports(int page, int size);

    Page<Report> getReportsByType(Long typeId, int page, int size);

    Report readReport(Long id);

}
