package kr.co.yigil.report.report.domain;

import org.springframework.data.domain.Page;

public interface ReportReader {

    Page<Report> getReportsByType(Long typeId, int page, int size);
    Report getReportById(Long id);

    Page<Report> getAllReports(int page, int size);
}
