package kr.co.yigil.report.report.domain;

import kr.co.yigil.report.report.domain.dto.ReportCommand;
import kr.co.yigil.report.report.domain.dto.ReportInfo;
import org.springframework.data.domain.Pageable;

public interface ReportService {
    void submitReport(ReportCommand.CreateCommand command);

    ReportInfo.ReportDetailInfo getMemberReport(Long memberId, Long id);

    ReportInfo.ReportsInfo getMemberReportList(Long memberId, Long typeId, String keyword, Pageable pageable);

    void deleteReport(Long memberId, Long id);

    ReportInfo.ReportTypesInfo getReportTypes();
}
