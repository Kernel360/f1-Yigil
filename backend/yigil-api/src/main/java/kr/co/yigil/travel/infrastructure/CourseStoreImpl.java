package kr.co.yigil.travel.infrastructure;

import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseStore;
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
}
