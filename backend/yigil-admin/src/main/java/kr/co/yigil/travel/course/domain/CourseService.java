package kr.co.yigil.travel.course.domain;

import kr.co.yigil.travel.course.domain.CourseInfoDto.CourseDetailInfo;
import kr.co.yigil.travel.course.domain.CourseInfoDto.CoursesPageInfo;
import org.springframework.data.domain.PageRequest;

public interface CourseService {

    CoursesPageInfo getCourses(PageRequest pageRequest);

    CourseDetailInfo getCourse(Long courseId);

    Long deleteCourse(Long courseId);
}
