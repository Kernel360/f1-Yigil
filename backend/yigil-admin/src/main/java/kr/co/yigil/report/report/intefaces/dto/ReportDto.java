package kr.co.yigil.report.report.intefaces.dto;

import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.report.report.domain.ProcessStatus;
import kr.co.yigil.report.type.domain.ReportType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ReportDto {


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportReplyRequest {
        private String content;
    }

    @Data
    @Builder
    public static class ReportsResponse {
        List<ReportListDto> reports;
        int totalPage;
    }

    @Data
    @Builder
    public static class ReportListDto {
        private ReportDetailDto report;
        private ReporterDetailDto reporter;
    }

    @Data
    @Builder
    public static class ReportDetailDto {
        private Long id;
        private ReportType reportType;
        private String content;
        private ProcessStatus status;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    public static class ReporterDetailDto {
        private Long id;
        private String name;
        private String email;
        private SocialLoginType socialLoginType;
    }


    /**
     * process result response
     *
     */
    @Data
    @AllArgsConstructor
    public static class ProcessResultResponse{
        private String message;
    }
}
