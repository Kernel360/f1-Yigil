package kr.co.yigil.report.report.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final ReportReader reportReader;
    private final ReportStore reportStore;

    @Override
    public Page<Report> getAllReports(int page, int size) {
        return reportReader.getAllReports(page, size);
    }

    @Override
    public Page<Report> getReportsByType(Long type, int page, int size) {
        return reportReader.getReportsByType(type, page, size);
    }

    @Override
    @Transactional
    public Report readReport(Long id) {
        return reportReader.getReportById(id);
    }

    @Override
    @Transactional
    public void setProcessing(Long id) {
        reportStore.setProcessing(id);
    }
}
