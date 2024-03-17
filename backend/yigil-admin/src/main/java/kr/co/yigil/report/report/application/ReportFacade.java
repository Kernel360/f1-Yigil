package kr.co.yigil.report.report.application;

import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import kr.co.yigil.report.report.domain.ReportCommand;
import kr.co.yigil.report.report.domain.ReportInfo;
import kr.co.yigil.report.report.domain.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportFacade {
    private final ReportService reportService;
    private final NotificationService notificationService;
    private final AdminService adminService;

    public ReportInfo.ReportPageInfo getReports(Pageable pageable, Long memberId, Long typeId, String keyword) {
        return reportService.getAllReports(pageable, memberId, typeId, keyword);
    }

    public void replyToReport(ReportCommand.ReportReplyCommand command) {
        Long reporterId = reportService.processReport(command);
        notificationService.saveNotification(NotificationType.REPORT_REPLY, adminService.getAdminId(), reporterId);
    }

    public void deleteReport(Long id) {
        reportService.deleteReport(id);
        notificationService.saveNotification(NotificationType.REPORT_DELETED, adminService.getAdminId(), id);
    }
}
