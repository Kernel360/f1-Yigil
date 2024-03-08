package kr.co.yigil.travel.domain.course;

import static kr.co.yigil.global.exception.ExceptionCode.INVALID_AUTHORITY;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.favor.domain.FavorReader;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.global.Selected;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequestWithSpotInfo;
import kr.co.yigil.travel.domain.course.CourseInfo.CourseSearchInfo;
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
    private final FavorReader favorReader;

    private final CourseStore courseStore;

    private final CourseSeriesFactory courseSeriesFactory;
    private final CourseSpotSeriesFactory courseSpotSeriesFactory;

    private final FileUploader fileUploader;

    @Override
    @Transactional(readOnly = true)
    public Slice<Course> getCoursesSliceInPlace(final Long placeId, final Pageable pageable) {
        return courseReader.getCoursesSliceInPlace(placeId, pageable);
    }

    @Override
    @Transactional
    public void registerCourse(final RegisterCourseRequest command, final Long memberId) {
        Member member = memberReader.getMember(memberId);
        var spots = courseSpotSeriesFactory.store(command, memberId);
        var mapStaticImage = fileUploader.upload(command.getMapStaticImageFile());
        var initCourse = command.toEntity(spots, member, mapStaticImage);
        courseStore.store(initCourse);
    }

    @Override
    @Transactional
    public void registerCourseWithoutSeries(final RegisterCourseRequestWithSpotInfo command,
            final Long memberId) {
        Member member = memberReader.getMember(memberId);
        var spots = courseSpotSeriesFactory.store(command, memberId);
        var mapStaticImage = fileUploader.upload(command.getMapStaticImageFile());
        var initCourse = command.toEntity(spots, member, mapStaticImage);
        courseStore.store(initCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public Main retrieveCourseInfo(final Long courseId) {
        var course = courseReader.getCourse(courseId);
        return new Main(course);
    }

    @Override
    @Transactional
    public Course modifyCourse(final ModifyCourseRequest command, final Long courseId, final Long memberId) {
        var course = courseReader.getCourse(courseId);
        if(!Objects.equals(course.getMember().getId(), memberId)) throw new AuthException(INVALID_AUTHORITY);
        return courseSeriesFactory.modify(command, course);
    }

    @Override
    @Transactional
    public void deleteCourse(final Long courseId, final Long memberId) {
        var course = courseReader.getCourse(courseId);
        if(!Objects.equals(course.getMember().getId(), memberId)) throw new AuthException(INVALID_AUTHORITY);
        courseStore.remove(course);
        course.getSpots().forEach(Spot::changeOutOfCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseInfo.MyCoursesResponse retrieveCourseList(final Long memberId, final Pageable pageable,
        Selected visibility) {
        var pageCourse = courseReader.getMemberCourseList(memberId, pageable, visibility);
        List<CourseInfo.CourseListInfo> courseInfoList = pageCourse.getContent().stream()
            .map(CourseInfo.CourseListInfo::new)
            .toList();
        return new CourseInfo.MyCoursesResponse(courseInfoList, pageCourse.getTotalPages());
    }

    @Override
    @Transactional(readOnly = true)
    public CourseInfo.Slice searchCourseByPlaceName(String keyword, Accessor accessor,
            Pageable pageable) {
        var result = courseReader.searchCourseByPlaceName(keyword, pageable);
        var courses = result.getContent().stream()
                .map(course -> {
                    boolean isLiked = accessor.isMember() && favorReader.existsByMemberIdAndTravelId(accessor.getMemberId(), course.getId());
                    return new CourseSearchInfo(course, isLiked);
                }).toList();
        return new CourseInfo.Slice(courses, result.hasNext());
    }
}
