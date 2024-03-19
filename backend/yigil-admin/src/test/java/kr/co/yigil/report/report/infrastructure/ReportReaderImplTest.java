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
import org.springframework.data.domain.PageRequest;

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

//    @DisplayName("getReportsByType 메서드가 ReportRepository를 잘 호출하는지")
//    @Test
//    void getReportsByType() {
//        reportReader.getReportsByType(1L, 0, 5);
//        verify(reportRepository).findAllByReportTypeId(1L, PageRequest.of(0, 5, Sort.by("createdAt").descending()));
//    }

    @DisplayName("getReportById 메서드가 ReportRepository를 잘 호출하는지")
    @Test
    void getReportById() {
        Report report = mock(Report.class);
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));
        var result = reportReader.getReportById(1L);
        assertThat(result).isEqualTo(report);
    }

    @DisplayName("getReportById 메서드가 ReportRepository를 잘 호출하는지")
    @Test
    void getReportById_thenShouldThrowAnError() {
        when(reportRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> reportReader.getReportById(1L));
    }

    @Test
    void getAllReports() {
        Report report = mock(Report.class);
        Page<Report> pageReport = new PageImpl<>(List.of(report));
        when(reportRepository.findAllByMemberIdByReportTypeIdAndContentContains(1L, 1L, "keyword", PageRequest.of(0, 5))).thenReturn(pageReport);

        var result = reportReader.getAllReports(PageRequest.of(0, 5), 1L, 1L, "keyword");
        assertThat(result).isEqualTo(pageReport);
    }
}