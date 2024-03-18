package kr.co.yigil.report.report.domain;

import kr.co.yigil.admin.domain.adminSignUp.EmailSender;
import kr.co.yigil.member.Member;
import kr.co.yigil.report.type.domain.ReportType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private ReportStore reportStore;

    @Mock
    private ReportReader reportReader;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private ReportServiceImpl reportService;


    @Test
    void getAllReports() {

        Report report1 = mock(Report.class);
        when(report1.getId()).thenReturn(1L);
        when(report1.getReporter()).thenReturn(mock(Member.class));
        when(report1.getReportType()).thenReturn(mock(ReportType.class));
        when(report1.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(report1.getContent()).thenReturn("content");
        when(report1.getStatus()).thenReturn(ProcessStatus.NOT_PROCESSED);
        List<Report> reports = List.of(report1);
        Page<Report> reportPage = new PageImpl(reports);
        when(reportReader.getAllReports(null, null, null, null)).thenReturn(reportPage);

        var result = reportService.getAllReports(null, null, null, null);

        assertThat(result).isInstanceOf(ReportInfo.ReportPageInfo.class);
        assertThat(result.getReports().size()).isEqualTo(1);

    }

    @Test
    void processReport() {
        Long memberId = 1L;
        Long reportId = 1L;
        ReportCommand.ReportReplyCommand command = new ReportCommand.ReportReplyCommand(reportId, "content");

        Member mockMember = mock(Member.class);
        when(mockMember.getId()).thenReturn(memberId);

        Report report = mock(Report.class);
        when(reportReader.getReportById(1L)).thenReturn(report);

        when(report.getReporter()).thenReturn(mockMember);
        when(mockMember.getId()).thenReturn(memberId);

        var result = reportService.processReport(command);

        assertThat(result).isInstanceOf(Long.class);
        verify(emailSender).replyReport(command.getContent(), report);
        verify(report).completed();
        verify(reportStore).save(report);
    }

    @Test
    void deleteReport() {
        Long reportId = 1L;
        Report report = mock(Report.class);
        when(reportReader.getReportById(reportId)).thenReturn(report);

        reportService.deleteReport(reportId);

        verify(reportStore).delete(report);
    }
}