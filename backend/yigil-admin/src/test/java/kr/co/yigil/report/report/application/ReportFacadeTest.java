package kr.co.yigil.report.report.application;

import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import kr.co.yigil.report.report.domain.ReportCommand;
import kr.co.yigil.report.report.domain.ReportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReportFacadeTest {
    @Mock
    private ReportService reportService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private AdminService adminService;

    @InjectMocks
    private ReportFacade reportFacade;

    @DisplayName("getReports 메서드가 ReportService를 잘 호출하는지")
    @Test
    void getReports() {
        PageRequest pageable = PageRequest.of(0, 5);
        Long memberId = 1L;
        Long typeId = 1L;
        String keyword = "keyword";

        reportFacade.getReports(pageable, memberId, typeId, keyword);

        verify(reportService).getAllReports(pageable, memberId, typeId, keyword);

    }

    @DisplayName("replyToReport 메서드가 ReportService와 NotificationService를 잘 호출하는지")
    @Test
    void replyToReport() {
        ReportCommand.ReportReplyCommand command = new ReportCommand.ReportReplyCommand(1L, "reply");

        reportFacade.replyToReport(command);

        verify(reportService).processReport(command);
        verify(notificationService).saveNotification(any(NotificationType.class), anyLong(), anyLong());
    }

    @DisplayName("deleteReport 메서드가 ReportService와 NotificationService를 잘 호출하는지")
    @Test
    void deleteReport() {
        Long id = 1L;

        reportFacade.deleteReport(id);

        verify(reportService).deleteReport(id);
        verify(notificationService).saveNotification(any(NotificationType.class), anyLong(), anyLong());
    }
}