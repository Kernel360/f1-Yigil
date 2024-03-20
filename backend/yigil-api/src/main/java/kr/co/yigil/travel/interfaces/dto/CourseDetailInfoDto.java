package kr.co.yigil.travel.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailInfoDto {

    private String title;
    private double rate;
    private String mapStaticImageUrl;
    private String description;
    private String createdDate;
    private String lineStringJson;
    List<CourseSpotInfoDto> spots;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseSpotInfoDto {
        private Long id;
        private String order;
        private String placeName;
        private String placeAddress;
        private List<String> imageUrlList;
        private double rate;
        private String description;
        private String createDate;
    }
}
