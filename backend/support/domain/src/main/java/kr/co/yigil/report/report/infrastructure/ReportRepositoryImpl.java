package kr.co.yigil.report.report.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.yigil.report.report.domain.QReport;
import kr.co.yigil.report.report.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReportRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Page<Report> findAllByMemberIdByReportTypeIdAndContentContains(Long memberId, Long typeId, String contentKeyword, Pageable pageable) {
        QReport report = QReport.report;
        BooleanBuilder builder = new BooleanBuilder();

        if( memberId != null){
            builder.and(report.reporter.id.eq(memberId));
        }

        if (typeId != null) {
            builder.and(report.reportType.id.eq(typeId));
        }

        if (contentKeyword != null) {
            builder.and(report.content.contains(contentKeyword));
        }


        List<Report> reports = queryFactory
                .selectFrom(report)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(report.id.count())
                .from(report)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(reports, pageable, total);
    }
}
