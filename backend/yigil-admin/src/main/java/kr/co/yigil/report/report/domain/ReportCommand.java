package kr.co.yigil.report.report.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReportCommand {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportReplyCommand{
        private Long reportId;
        private String content;
    }
}
