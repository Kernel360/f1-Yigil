package kr.co.yigil.travel.interfaces.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseListResponse {
    private List<CourseFindDto> courseFindDtos;

    public static CourseListResponse from(List<CourseFindDto> courses) {
        return new CourseListResponse(
                courses
        );
    }

}
