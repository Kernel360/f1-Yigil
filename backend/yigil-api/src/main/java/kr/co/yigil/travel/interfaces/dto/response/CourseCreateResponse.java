package kr.co.yigil.travel.interfaces.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreateResponse {
    private Long courseId;
    private String message;
}
