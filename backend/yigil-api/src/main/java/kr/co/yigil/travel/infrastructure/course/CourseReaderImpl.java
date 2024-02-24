package kr.co.yigil.travel.infrastructure.course;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.MemberInfo;
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
    public MemberInfo.MemberCourseResponse getMemberCourseList(Long memberId, Pageable pageable,
        String visibility) {
        Page<Course> pageCourse;
        if (visibility.equals("all")) {
            pageCourse = courseRepository.findAllByMemberId(memberId, pageable);
        } else {
            pageCourse = courseRepository.findAllByMemberIdAndIsPrivate(memberId, visibility.equals("private"), pageable);
        }
        List<MemberInfo.CourseInfo> courseInfoList = pageCourse.getContent().stream()
            .map(MemberInfo.CourseInfo::new)
            .toList();
        return new MemberInfo.MemberCourseResponse(courseInfoList, pageCourse.getTotalPages());
    }
}
