package kr.co.yigil.travel.course.infrastructure;

import kr.co.yigil.travel.course.domain.CourseStore;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CourseStoreImpl implements CourseStore {
    private final CourseRepository courseRepository;

    @Override
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }
}
