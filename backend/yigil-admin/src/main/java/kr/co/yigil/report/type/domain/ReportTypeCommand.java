package kr.co.yigil.report.type.domain;


import lombok.Builder;
import lombok.Data;

public class ReportTypeCommand {

    @Data
    @Builder
    public static class CreateReportType {
        private String name;
        private String description;

        public ReportType toEntity() {
            return new ReportType(name, description);
        }
    }

}
