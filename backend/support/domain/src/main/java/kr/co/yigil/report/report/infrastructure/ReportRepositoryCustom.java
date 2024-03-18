package kr.co.yigil.report.report.infrastructure;

import kr.co.yigil.report.report.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportRepositoryCustom {
    Page<Report> findAllByMemberIdByReportTypeIdAndContentContains(Long memberId, Long typeId, String contentKeyword, Pageable pageable);

}
