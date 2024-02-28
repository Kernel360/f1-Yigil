package kr.co.yigil.travel.infrastructure.course;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseReader;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseReaderImpl implements CourseReader {

    private final CourseRepository courseRepository;

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_COURSE_ID));
    }

    @Override
    public Slice<Course> getCoursesSliceInPlace(Long placeId, Pageable pageable) {
        return courseRepository.findBySpotPlaceId(placeId, pageable);
    }

    @Override
    public Page<Course> getMemberCourseList(Long memberId, Pageable pageable,
        String visibility) {
        if (visibility.equals("all")) {
            return courseRepository.findAllByMemberId(memberId, pageable);
        }
        return courseRepository.findAllByMemberIdAndIsPrivate(memberId, visibility.equals("private"), pageable);
    }
}
