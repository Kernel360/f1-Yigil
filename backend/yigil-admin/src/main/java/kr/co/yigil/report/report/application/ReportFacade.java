package kr.co.yigil.report.report.application;

import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import kr.co.yigil.report.report.domain.Report;
import kr.co.yigil.report.report.domain.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportFacade {
    private final ReportService reportService;
    private final NotificationService notificationService;

    public Page<Report> getReports( int page, int size) {
        return reportService.getAllReports( page, size);
    }

    public Page<Report> getReportsByType(Long typeId, int page, int size) {
        return reportService.getReportsByType(typeId, page, size);
    }

    public Report readReport(Long id) {

        // todo: admin id token에서 가져오기
        Long adminId = 1L;
        notificationService.sendNotification(NotificationType.REPORT_READ, adminId, id);
        return reportService.readReport(id);
    }
    public void deleteReport(Long id) {
        reportService.deleteReport(id);
    }

    public void rejectReport(Long id) {
        Long adminId = 1L;//todo: admin id token에서 가져오기
        notificationService.sendNotification(NotificationType.REPORT_REJECTED, adminId, id);
        reportService.rejectReport(id);
    }


}
