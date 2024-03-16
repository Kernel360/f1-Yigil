package kr.co.yigil.report.report.domain;

import kr.co.yigil.admin.domain.adminSignUp.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportReader reportReader;
    private final ReportStore reportStore;
    private final EmailSender emailSender;

    @Override
    public ReportInfo.ReportPageInfo getAllReports(Pageable pageable, Long memberId, Long typeId, String keyword) {
        Page<Report> reports = reportReader.getAllReports(pageable, memberId, typeId, keyword);
        return new ReportInfo.ReportPageInfo(reports);
    }

    @Override
    @Transactional
    public Long processReport(ReportCommand.ReportReplyCommand command) {
        Report report = reportReader.getReportById(command.getReportId());
        emailSender.replyReport(command.getContent(), report);
        report.completed();
        reportStore.save(report);
        return report.getReporter().getId();
    }

    @Override
    @Transactional
    public void deleteReport(Long id) {
        Report report = reportReader.getReportById(id);
        reportStore.delete(report);
    }

}
