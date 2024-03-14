package kr.co.yigil.report.type.domain;

import java.util.List;
import kr.co.yigil.report.type.domain.ReportTypeCommand.CreateReportType;

public interface ReportTypeService {

    List<ReportType> getCategories();

    void createReportType(CreateReportType command);

    void deleteReportType(Long categoryId);
}
