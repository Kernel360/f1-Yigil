package kr.co.yigil.report.report.domain;

public interface ReportStore {
    void save(Report report);

    void delete(Report report);
}
