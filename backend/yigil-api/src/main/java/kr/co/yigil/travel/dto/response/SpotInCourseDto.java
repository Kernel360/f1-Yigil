package kr.co.yigil.travel.dto.response;

import kr.co.yigil.travel.Spot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotInCourseDto {
    private String title;
    //  todo file_uirl   추가, spotid
    private String description;

    public static SpotInCourseDto from(Spot spot) {
        return new SpotInCourseDto(spot.getTitle(), spot.getDescription());

    }
}
