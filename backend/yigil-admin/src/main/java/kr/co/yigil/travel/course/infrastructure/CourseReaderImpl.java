package kr.co.yigil.travel.course.infrastructure;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.course.domain.CourseReader;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CourseReaderImpl implements CourseReader {
    private final CourseRepository courseRepository;

    @Override
    public Page<Course> getCourses(PageRequest pageRequest) {
        return courseRepository.findAll(pageRequest);
    }

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(
            () -> new BadRequestException(ExceptionCode.NOT_FOUND_COURSE_ID)
        );
    }
}
