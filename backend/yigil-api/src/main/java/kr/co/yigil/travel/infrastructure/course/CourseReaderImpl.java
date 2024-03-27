package kr.co.yigil.travel.infrastructure.course;

import kr.co.yigil.global.Selected;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseReader;
import kr.co.yigil.travel.domain.dto.CourseListDto;
import kr.co.yigil.travel.infrastructure.CourseQueryDslRepository;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseReaderImpl implements CourseReader {

    private final CourseRepository courseRepository;
    private final CourseQueryDslRepository courseQueryDslRepository;

    @Override
    public Course getCourse(final Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_COURSE_ID));
    }

    @Override
    public Slice<Course> getCoursesSliceInPlace(final Long placeId, final Pageable pageable) {
        return courseRepository.findBySpots_PlaceIdAndIsPrivateFalse(placeId, pageable);
    }

    @Override
    public Page<CourseListDto> getMemberCourseList(final Long memberId, final Pageable pageable,
        final Selected visibility) {
        return courseQueryDslRepository.findAllByMemberIdAndIsPrivate(memberId, visibility, pageable);
    }

    @Override
    public Slice<Course> searchCourseByPlaceName(final String keyword, final Pageable pageable) {
        return courseRepository.findByPlaceNameContaining(keyword, pageable);
    }

    @Override
    public Page<Course> getFavoriteCourses(Long memberId, PageRequest pageRequest) {
        return courseRepository.findAllMembersFavoriteCourses(memberId, pageRequest);
    }
}
