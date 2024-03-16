package kr.co.yigil.report.report.interfaces.dto;

import kr.co.yigil.report.type.domain.ReportType;
import lombok.*;

import java.util.List;

public class ReportDto {


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TravelReportCreateRequest {
        private Long reportTypeId;
        private String content;
        private Long travelId;
    }

    @Data
    public static class ReportTypeResponse {
        private List<ReportTypeDto> reportTypes;
    }

    @Data
    public static class ReportTypeDto {
        private Long id;
        private String name;
    }

    @Data
    public static class MyReportsResponse {
        private List<MyReportDetail> myReports;
        int totalPage;
    }

    @Data
    public static class MyReportDetail {
        private Long id;
        private ReportType reportType;
        private String createdAt;
    }

    @Data
    @AllArgsConstructor
    public static class ReportResponse {
        private String message;
    }
}
