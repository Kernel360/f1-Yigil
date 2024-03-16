package kr.co.yigil.report.type.domain;

import java.util.List;

public interface ReportTypeReader {
    List<ReportType> getReportTypes();

    ReportType getReportType(Long reportTypeId);
}
