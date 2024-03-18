package kr.co.yigil.report.type.interfaces.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReportTypeDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportTypeCreateRequest {
        private String name;
        private String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportTypeListResponse {
        private List<TypeListInfo> categoryList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TypeListInfo {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    public static class ReportTypeResponse {
        private String message;
    }
}