package kr.co.yigil.report.report.application;


import kr.co.yigil.report.report.domain.ReportService;
import kr.co.yigil.report.report.domain.dto.ReportCommand;
import kr.co.yigil.report.report.domain.dto.ReportInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportFacade {
    private final ReportService reportService;

    public void submitReport(ReportCommand.CreateCommand command) {
        reportService.submitReport(command);
    }

    public ReportInfo.ReportsInfo getMemberReportListInfo(Long memberId, Long typeId, String keyword, Pageable pageable) {
        return reportService.getMemberReportList(memberId, typeId, keyword, pageable);
    }

    public ReportInfo.ReportDetailInfo getMemberReportInfo(Long memberId, Long id) {
        return reportService.getMemberReport(memberId, id);
    }

    public void deleteReport(Long memberId, Long id) {
        reportService.deleteReport(memberId, id);
    }

    public ReportInfo.ReportTypesInfo getReportTypes() {
        return reportService.getReportTypes();
    }
}
