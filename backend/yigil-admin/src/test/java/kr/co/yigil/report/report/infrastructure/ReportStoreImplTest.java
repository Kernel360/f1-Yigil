package kr.co.yigil.report.report.infrastructure;

import kr.co.yigil.report.report.domain.Report;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReportStoreImplTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportStoreImpl reportStore;

    @Test
    void deleteAll() {
        List<Long> reportIds =  List.of(1L, 2L, 3L);
        reportStore.deleteAll(reportIds);
        verify(reportRepository).deleteAllById(reportIds);
    }

    @Test
    void save() {
        Report mockReport = mock(Report.class);
        reportStore.save(mockReport);
        verify(reportRepository).save(mockReport);
    }

    @Test
    void delete() {
        Report mockReport = mock(Report.class);
        reportStore.delete(mockReport);
        verify(reportRepository).delete(mockReport);
    }
}