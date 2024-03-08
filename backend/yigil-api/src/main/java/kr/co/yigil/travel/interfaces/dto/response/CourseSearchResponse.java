package kr.co.yigil.travel.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.travel.interfaces.dto.CourseInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseSearchResponse {
    private List<CourseInfoDto> courses;
    private boolean hasNext;
}
