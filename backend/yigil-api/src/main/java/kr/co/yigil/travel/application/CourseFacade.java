package kr.co.yigil.travel.application;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.Selected;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequestWithSpotInfo;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.domain.course.CourseService;
import kr.co.yigil.travel.domain.spot.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseFacade {
    private final CourseService courseService;
    private final SpotService spotService;

    public CourseInfo.CoursesInPlaceResponseInfo getCourseSliceInPlace(Long placeId, Long memberId, Pageable pageable) {
        return courseService.getCoursesSliceInPlace(placeId, memberId, pageable);
    }

    public void registerCourse(RegisterCourseRequest command, Long memberId) {
        courseService.registerCourse(command, memberId);
    }

    public void registerCourseWithoutSeries(RegisterCourseRequestWithSpotInfo command, Long memberId) {
        courseService.registerCourseWithoutSeries(command, memberId);
    }

    public CourseInfo.Main retrieveCourseInfo(Long courseId) {
        return courseService.retrieveCourseInfo(courseId);
    }

    public void modifyCourse(ModifyCourseRequest command, Long courseId, Long memberId) {
        courseService.modifyCourse(command, courseId, memberId);
    }

    public void deleteCourse(Long courseId, Long memberId) {
        courseService.deleteCourse(courseId, memberId);
    }

    public CourseInfo.MyCoursesResponse getMemberCoursesInfo(final Long memberId,
                                                             Pageable pageable, Selected selected) {
        return courseService.retrieveCourseList(memberId, pageable, selected);
    }

    public CourseInfo.Slice searchCourseByPlaceName(final String keyword, final Accessor accessor, final Pageable pageable) {
        return courseService.searchCourseByPlaceName(keyword, accessor, pageable);
    }

    public CourseInfo.MySpotsInfo getMySpotsDetailInfo(List<Long> spotIds, Long memberId) {
        return spotService.getMySpotsDetailInfo(spotIds, memberId);
    }
}
