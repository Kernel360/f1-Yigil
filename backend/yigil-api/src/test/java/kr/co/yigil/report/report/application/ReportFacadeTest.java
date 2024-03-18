package kr.co.yigil.report.report.application;

import kr.co.yigil.report.report.domain.ReportService;
import kr.co.yigil.report.report.domain.dto.ReportCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReportFacadeTest {
    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportFacade reportFacade;


    @DisplayName("submitReport 메서드가 ReportService를 잘 호출하는지")
    @Test
    void submitReport() {
        ReportCommand.CreateCommand command = mock(ReportCommand.CreateCommand.class);

        reportFacade.submitReport(command);

        verify(reportService).submitReport(command);
    }

    @DisplayName("getMemberReportListInfo 메서드가 ReportService를 잘 호출하는지")
    @Test
    void getMemberReportListInfo() {
        Long memberId = 1L;
        Long typeId = 1L;
        String keyword = "keyword";
        PageRequest pageable = PageRequest.of(0, 5);

        reportFacade.getMemberReportListInfo(memberId, typeId, keyword, pageable);

        verify(reportService).getMemberReportList(memberId, typeId, keyword, pageable);
    }

    @DisplayName("getMemberReportInfo 메서드가 ReportService를 잘 호출하는지")
    @Test
    void getMemberReportInfo() {
        Long memberId = 1L;
        Long id = 1L;

        reportFacade.getMemberReportInfo(memberId, id);

        verify(reportService).getMemberReport(memberId, id);
    }

    @DisplayName("deleteReport 메서드가 ReportService를 잘 호출하는지")
    @Test
    void deleteReport() {
        Long memberId = 1L;
        Long id = 1L;

        reportFacade.deleteReport(memberId, id);

        verify(reportService).deleteReport(memberId, id);
    }

    @DisplayName("getReportTypes 메서드가 ReportService를 잘 호출하는지")
    @Test
    void getReportTypes() {
        reportFacade.getReportTypes();

        verify(reportService).getReportTypes();
    }
}