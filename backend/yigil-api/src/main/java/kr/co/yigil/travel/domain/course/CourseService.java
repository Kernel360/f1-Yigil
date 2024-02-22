package kr.co.yigil.travel.domain.course;

import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseInfo.Main;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CourseService {

    Slice<Course> getCoursesSliceInPlace(Long placeId, Pageable pageable);
    void registerCourse(RegisterCourseRequest request, Long memberId);
    Main retrieveCourseInfo(Long courseId);
    void modifyCourse(ModifyCourseRequest command, Long courseId, Long memberId);
}
