package kr.co.yigil.report.report.domain.dto;

import kr.co.yigil.report.report.domain.ProcessStatus;
import kr.co.yigil.report.report.domain.Report;
import kr.co.yigil.report.type.domain.ReportType;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class ReportInfo {

    @Data
    public static class ReportsInfo {
        private final List<ReportListInfo> reports;
        private final int totalPage;

        public ReportsInfo(Page<Report> reports) {
            this.reports = reports.getContent().stream().map(ReportListInfo::new).toList();
            this.totalPage = reports.getTotalPages();
        }
    }

    @Data
    public static class ReportListInfo {
        private final Long id;
        private final ReportTypeInfo reportType;
        private final LocalDateTime createdAt;

        public ReportListInfo(Report report) {
            this.id = report.getId();
            this.reportType = new ReportTypeInfo(report.getReportType());
            this.createdAt = report.getCreatedAt();
        }
    }

    @Data
    public static class ReportTypeInfo {
        private final Long id;
        private final String name;

        public ReportTypeInfo(ReportType reportType) {
            this.id = reportType.getId();
            this.name = reportType.getName();
        }
    }

    @Data
    public static class ReportDetailInfo {
        private final Long id;
        private final ReportTypeInfo reportType;
        private final String content;
        private final ProcessStatus status;
        private final LocalDateTime createdAt;

        public ReportDetailInfo(Report report) {
            this.id = report.getId();
            this.reportType = new ReportTypeInfo(report.getReportType());
            this.content = report.getContent();
            this.status = report.getStatus();
            this.createdAt = report.getCreatedAt();
        }
    }

    @Getter
    public static class ReportTypesInfo {
        private final List<ReportTypeDetail> reportTypes;

        public ReportTypesInfo(List<ReportType> reportTypes) {
            this.reportTypes = reportTypes.stream().map(ReportTypeDetail::new).toList();
        }
    }

    @Data
    public static class ReportTypeDetail{
        private final Long id;
        private final String name;

        public ReportTypeDetail(ReportType reportType) {
            this.id = reportType.getId();
            this.name = reportType.getName();
        }
    }
}
