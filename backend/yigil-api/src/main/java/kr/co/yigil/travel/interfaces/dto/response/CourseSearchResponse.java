package kr.co.yigil.travel.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.travel.interfaces.dto.CourseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseSearchResponse {
    private List<CourseDto> courses;
    private boolean hasNext;
}
