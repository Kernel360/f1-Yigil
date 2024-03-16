package kr.co.yigil.report.report.domain;

import org.springframework.data.domain.Pageable;

public interface ReportService {

    ReportInfo.ReportPageInfo getAllReports(Pageable pageable, Long memberId, Long typeId, String keyword);

    Long processReport(ReportCommand.ReportReplyCommand command);

    void deleteReport(Long id);
}
