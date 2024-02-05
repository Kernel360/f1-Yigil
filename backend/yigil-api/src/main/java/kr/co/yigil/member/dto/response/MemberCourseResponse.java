package kr.co.yigil.member.dto.response;

import java.util.List;
import kr.co.yigil.travel.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCourseResponse {
    private List<Course> courseList;

    public static MemberCourseResponse from(final List<Course> courseList) {
        return new MemberCourseResponse(courseList);
    }
}
