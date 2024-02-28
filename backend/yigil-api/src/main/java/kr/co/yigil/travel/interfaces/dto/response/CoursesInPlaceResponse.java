package kr.co.yigil.travel.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.travel.interfaces.dto.CourseInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesInPlaceResponse {
    private List<CourseInfoDto> courses;
    private boolean hasNext;
}
