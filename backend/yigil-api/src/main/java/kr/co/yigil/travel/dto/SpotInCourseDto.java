package kr.co.yigil.travel.dto;

import kr.co.yigil.travel.domain.Spot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotInCourseDto {
    private String title;
    private String fileUrl;
    private String description;

    public static SpotInCourseDto from(Spot spot) {
        return new SpotInCourseDto(spot.getTitle(), spot.getFileUrl(), spot.getDescription());

    }
}
