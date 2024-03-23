package kr.co.yigil.report.report.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.report.type.domain.ReportType;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class ReportInfo {


    @Getter
    public static class ReportPageInfo {
        List<ReportListInfo> reports;
        int totalPage;

        public ReportPageInfo(Page<Report> reports) {
            this.reports = reports.getContent().stream().map(ReportListInfo::new).toList();
            this.totalPage = reports.getTotalPages();
        }
    }


    @Data
    @Getter
    public static class ReportListInfo {

        private ReportDetailInfo report;
        private ReporterDetailInfo reporter;

        public ReportListInfo(Report report) {
            this.report = new ReportDetailInfo(report);
            this.reporter = new ReporterDetailInfo(report.getReporter());
        }
    }

    @Data
    @Getter
    public static class ReportDetailInfo {
        private Long id;
        private ReportType reportType;
        private String content;
        private ProcessStatus status;
        private LocalDateTime createdAt;

        public ReportDetailInfo(Report report) {
            id = report.getId();
            reportType = report.getReportType();
            content = report.getContent();
            status = report.getStatus();
            createdAt = report.getCreatedAt();
        }
    }

    @Data
    @Getter
    public static class ReporterDetailInfo {
        private Long id;
        private String name;
        private String email;
        private SocialLoginType socialLoginType;

        public ReporterDetailInfo(Member member) {
            id = member.getId();
            name = member.getNickname();
            email = member.getEmail();
            socialLoginType = member.getSocialLoginType();
        }
    }

}
