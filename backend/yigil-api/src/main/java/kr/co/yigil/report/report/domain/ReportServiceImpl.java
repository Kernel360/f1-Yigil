package kr.co.yigil.report.report.domain;


import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.report.report.domain.dto.ReportCommand;
import kr.co.yigil.report.report.domain.dto.ReportInfo;
import kr.co.yigil.report.type.domain.ReportType;
import kr.co.yigil.report.type.domain.ReportTypeReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final ReportReader reportReader;
    private final ReportStore reportStore;
    private final MemberReader memberReader;
    private final ReportTypeReader reportTypeReader;


    @Override
    @Transactional
    public void submitReport(ReportCommand.CreateCommand command) {
        Member reporter = memberReader.getMember(command.getReporterId());
        ReportType reportType = reportTypeReader.getReportType(command.getReportTypeId());
        Report report = command.toEntity(reportType, reporter);
        reportStore.save(report);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportInfo.ReportDetailInfo getMemberReport(Long memberId, Long id) {
        Report report = reportReader.getMemberReport(memberId, id);
        return new ReportInfo.ReportDetailInfo(report);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportInfo.ReportsInfo getMemberReportList(Long memberId, Long typeId, String keyword, Pageable pageable) {
        Page<Report> reports = reportReader.getMemberReportList(memberId, typeId, keyword, pageable);
        return new ReportInfo.ReportsInfo(reports);
    }

    @Override
    @Transactional
    public void deleteReport(Long memberId, Long id) {
        Report report = reportReader.getMyReport(memberId, id);
        reportStore.delete(report);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportInfo.ReportTypesInfo getReportTypes() {
        return new ReportInfo.ReportTypesInfo(reportTypeReader.getReportTypes());
    }
}
