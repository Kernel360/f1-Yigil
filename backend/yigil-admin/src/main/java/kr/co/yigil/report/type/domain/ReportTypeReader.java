package kr.co.yigil.report.type.domain;

import java.util.List;

public interface ReportTypeReader {

    List<ReportType> getCategories();

    boolean existsByName(String name);

    boolean existsById(Long categoryId);
}
