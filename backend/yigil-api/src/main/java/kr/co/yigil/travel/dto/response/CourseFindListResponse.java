package kr.co.yigil.travel.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseFindListResponse {
    private List<CourseFindDto> courseFindDtos;

    public static CourseFindListResponse from(List<CourseFindDto> courses) {
        return new CourseFindListResponse(
                courses
        );
    }

}
