package kr.co.yigil.travel.course.domain;


import kr.co.yigil.travel.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CourseReader {

    Page<Course> getCourses(PageRequest pageRequest);

    Course getCourse(Long courseId);
}
