package kr.co.yigil.report.report.domain;

import java.util.List;

public interface ReportStore {

    void deleteAll(List<Long> reportIds);

    void save(Report report);

    void delete(Report report);
}
