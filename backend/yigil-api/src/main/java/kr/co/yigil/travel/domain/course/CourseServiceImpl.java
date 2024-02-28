package kr.co.yigil.travel.domain.course;

import static kr.co.yigil.global.exception.ExceptionCode.INVALID_AUTHORITY;

import java.util.List;
import java.util.Objects;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequestWithSpotInfo;
import kr.co.yigil.travel.domain.course.CourseInfo.Main;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final MemberReader memberReader;
    private final CourseReader courseReader;

    private final CourseStore courseStore;

    private final CourseSeriesFactory courseSeriesFactory;
    private final CourseSpotSeriesFactory courseSpotSeriesFactory;

    @Override
    public Slice<Course> getCoursesSliceInPlace(Long placeId, Pageable pageable) {
        return courseReader.getCoursesSliceInPlace(placeId, pageable);
    }

    @Override
    @Transactional
    public void registerCourse(RegisterCourseRequest command, Long memberId) {
        Member member = memberReader.getMember(memberId);
        var spots = courseSpotSeriesFactory.store(command, memberId);
        var initCourse = command.toEntity(spots, member);
        var course = courseStore.store(initCourse);
    }

    @Override
    @Transactional
    public void registerCourseWithoutSeries(RegisterCourseRequestWithSpotInfo command,
        Long memberId) {
        Member member = memberReader.getMember(memberId);
        var spots = courseSpotSeriesFactory.store(command, memberId);
        var initCourse = command.toEntity(spots, member);
        var course = courseStore.store(initCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public Main retrieveCourseInfo(Long courseId) {
        var course = courseReader.getCourse(courseId);
        return new Main(course);
    }

    @Override
    @Transactional
    public void modifyCourse(ModifyCourseRequest command, Long courseId, Long memberId) {
        var course = courseReader.getCourse(courseId);
        if (!Objects.equals(course.getMember().getId(), memberId)) {
            throw new AuthException(INVALID_AUTHORITY);
        }
        var modifedCourse = courseSeriesFactory.modify(command, course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long courseId, Long memberId) {
        var course = courseReader.getCourse(courseId);
        if (!Objects.equals(course.getMember().getId(), memberId)) {
            throw new AuthException(INVALID_AUTHORITY);
        }
        courseStore.remove(course);
        course.getSpots().forEach(Spot::changeOutOfCourse);
    }

    @Override
    @Transactional
    public CourseInfo.MyCoursesResponse retrieveCourseList(Long memberId, Pageable pageable,
        String visibility) {
        var pageCourse = courseReader.getMemberCourseList(memberId, pageable, visibility);
        List<CourseInfo.CourseListInfo> courseInfoList = pageCourse.getContent().stream()
            .map(CourseInfo.CourseListInfo::new)
            .toList();
        return new CourseInfo.MyCoursesResponse(courseInfoList, pageCourse.getTotalPages());
    }
}
