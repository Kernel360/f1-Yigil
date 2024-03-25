package kr.co.yigil.report.type.application;

import java.util.List;
import kr.co.yigil.report.type.domain.ReportTypeCommand.CreateReportType;
import kr.co.yigil.report.type.domain.ReportType;
import kr.co.yigil.report.type.domain.ReportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportTypeFacade {
    private final ReportTypeService reportTypeService;

    public List<ReportType> getCategories() {
        return reportTypeService.getCategories();
    }

    public void createReportType(CreateReportType command) {
        reportTypeService.createReportType(command);
    }

    public void deleteReportType(Long categoryId) {
        reportTypeService.deleteReportType(categoryId);
    }
}
