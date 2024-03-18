package kr.co.yigil.report.report.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportReader {
    Report getMemberReport(Long memberId, Long id);

    Page<Report> getMemberReportList(Long memberId, Long typeId, String keyword, Pageable pageable);

    Report getMyReport(Long memberId, Long id);
}
