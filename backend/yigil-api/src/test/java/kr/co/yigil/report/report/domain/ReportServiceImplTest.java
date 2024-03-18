package kr.co.yigil.report.report.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.report.report.domain.dto.ReportCommand;
import kr.co.yigil.report.report.domain.dto.ReportInfo;
import kr.co.yigil.report.type.domain.ReportType;
import kr.co.yigil.report.type.domain.ReportTypeReader;
import org.junit.jupiter.api.DisplayName;
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
    private MemberReader memberReader;

    @Mock
    private ReportTypeReader reportTypeReader;

    @InjectMocks
    private ReportServiceImpl reportService;

    @DisplayName("신고가 잘 등록되는지")
    @Test
    void submitReport() {
        // given
        ReportCommand.CreateCommand command = ReportCommand.CreateCommand.builder()
                .reporterId(1L)
                .reportTypeId(1L)
                .content("content")
                .travelId(1L)
                .build();
        Member reporter = mock(Member.class);
        ReportType reportType = mock(ReportType.class);
        when(memberReader.getMember(anyLong())).thenReturn(reporter);
        when(reportTypeReader.getReportType(anyLong())).thenReturn(reportType);

        // when
        reportService.submitReport(command);
        // then
        verify(reportStore).save(any());
    }

    @Test
    void getMemberReport() {
        ReportType reportType = mock(ReportType.class);
        String content = "content";
        Long reportId = 1L;
        ProcessStatus status = ProcessStatus.NOT_PROCESSED;
        LocalDateTime createdAt = LocalDateTime.now();
        Report report = mock(Report.class);
        when(report.getId()).thenReturn(reportId);
        when(report.getReportType()).thenReturn(reportType);
        when(report.getContent()).thenReturn(content);
        when(report.getStatus()).thenReturn(status);
        when(report.getCreatedAt()).thenReturn(createdAt);

        when(reportReader.getMemberReport(anyLong(), anyLong())).thenReturn(report);

        var reportInfo = reportService.getMemberReport(1L, 1L);
        assertThat(reportInfo).isInstanceOf(ReportInfo.ReportDetailInfo.class);
        assertThat(reportInfo.getReportType().getId()).isEqualTo(reportType.getId());
        assertThat(reportInfo.getReportType().getName()).isEqualTo(reportType.getName());
        assertThat(reportInfo.getContent()).isEqualTo(content);
        assertThat(reportInfo.getId()).isEqualTo(reportId);
        assertThat(reportInfo.getStatus()).isEqualTo(status);
    }

    @Test
    void getMemberReportList() {
        Report report = mock(Report.class);

        Page<Report> reportPage = new PageImpl<>(List.of(report));
        when(reportReader.getMemberReportList(anyLong(), anyLong(), anyString(), any())).thenReturn(reportPage);

        when(report.getId()).thenReturn(1L);
        when(report.getReportType()).thenReturn(mock(ReportType.class));
        when(report.getCreatedAt()).thenReturn(LocalDateTime.now());

        var reportsInfo = reportService.getMemberReportList(1L, 1L, "keyword", null);
        assertThat(reportsInfo).isInstanceOf(ReportInfo.ReportsInfo.class);
        assertThat(reportsInfo.getReports().size()).isEqualTo(1);

    }

    @Test
    void deleteReport() {
        Report report = mock(Report.class);
        when(reportReader.getMyReport(anyLong(), anyLong())).thenReturn(report);
        reportService.deleteReport(1L, 1L);
        verify(reportStore).delete(report);
    }

    @Test
    void getReportTypes() {
        ReportType reportType = mock(ReportType.class);
        when(reportTypeReader.getReportTypes()).thenReturn(List.of(reportType));
        var reportTypesInfo = reportService.getReportTypes();
        assertThat(reportTypesInfo).isInstanceOf(ReportInfo.ReportTypesInfo.class);
        assertThat(reportTypesInfo.getReportTypes().size()).isEqualTo(1);
    }
}