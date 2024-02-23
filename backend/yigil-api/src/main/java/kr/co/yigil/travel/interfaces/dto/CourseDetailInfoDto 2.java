package kr.co.yigil.travel.interfaces.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailInfoDto {
    private String title;
    private String rate;
    private String mapStaticImageUrl;
    private String description;
    List<CourseSpotInfoDto> spots;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseSpotInfoDto {
        private String order;
        private String placeName;
        private List<String> imageUrlList;
        private String rate;
        private String description;
        private String createDate;
    }
}
