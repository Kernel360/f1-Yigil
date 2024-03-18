package kr.co.yigil.report.type.domain;

import kr.co.yigil.report.type.domain.ReportTypeCommand.CreateReportType;

public interface ReportTypeStore {

    void createCategory(CreateReportType command);

    void deleteCategory(Long categoryId);
}
