package kr.co.yigil.travel.domain.course;

import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;

public interface CourseSeriesFactory {
    Course modify(ModifyCourseRequest courseRequest, Course course);
}
