package kr.co.yigil.travel.infrastructure.course;

import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseStore;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseStoreImpl implements CourseStore {
    private final CourseRepository courseRepository;

    @Override
    public Course store(Course initCourse) {
        return courseRepository.save(initCourse);
    }

    @Override
    public void remove(Course course) {
        courseRepository.delete(course);
    }
}
