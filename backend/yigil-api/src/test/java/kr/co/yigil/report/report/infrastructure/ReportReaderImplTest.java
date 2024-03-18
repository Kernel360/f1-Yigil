package kr.co.yigil.report.report.infrastructure;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.report.report.domain.Report;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportReaderImplTest {
    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportReaderImpl reportReader;


    @DisplayName("getMemberReport 메서드가 ReportRepository를 잘 호출하는지")
    @Test
    void getMemberReport() {
        Long memberId = 1L;
        Long id = 1L;

        Report report = mock(Report.class);
        when(reportRepository.findByIdAndReporterId(id, memberId)).thenReturn(
                Optional.of(report));

        var result = reportReader.getMemberReport(memberId, id);

        assertThat(result).isEqualTo(report);

    }

    @Test
    void getMemberReport_thenShouldThrowAnError() {
        Long memberId = 1L;
        Long id = 1L;
        ;
        when(reportRepository.findByIdAndReporterId(id, memberId)).thenReturn(
                Optional.empty());

        assertThrows(BadRequestException.class, ()-> reportReader.getMemberReport(memberId, id));

    }
    @Test
    void getMemberReportList() {
        Report report = mock(Report.class);
        Page<Report> pageReport = new PageImpl<>(List.of(report));
        when(reportRepository.findAllByMemberIdByReportTypeIdAndContentContains(1L, 1L, "keyword", null)).thenReturn(pageReport);
        var result = reportReader.getMemberReportList(1L, 1L, "keyword", null);
        assertThat(result).isEqualTo(pageReport);
    }

    @Test
    void getMyReport() {
        Long memberId = 1L;
        Long id = 1L;

        Report report = mock(Report.class);
        when(reportRepository.findByIdAndReporterId(id, memberId)).thenReturn(
                Optional.of(report));

        var result = reportReader.getMyReport(memberId, id);

        assertThat(result).isEqualTo(report);
    }
}