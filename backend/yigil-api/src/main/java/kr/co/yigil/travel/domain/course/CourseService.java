package kr.co.yigil.travel.domain.course;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.Selected;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequestWithSpotInfo;
import kr.co.yigil.travel.domain.course.CourseInfo.Main;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    CourseInfo.CoursesInPlaceResponseInfo getCoursesSliceInPlace(final Long placeId, final Long memberId,final Pageable pageable);
    void registerCourse(RegisterCourseRequest request, Long memberId);
    void registerCourseWithoutSeries(RegisterCourseRequestWithSpotInfo request, Long memberId);
    Main retrieveCourseInfo(Long courseId);
    Course modifyCourse(ModifyCourseRequest command, Long courseId, Long memberId);
    void deleteCourse(Long courseId, Long memberId);

    CourseInfo.MyCoursesResponse retrieveCourseList(Long memberId, Pageable pageable, Selected selected);
    CourseInfo.Slice searchCourseByPlaceName(String keyword, Accessor accessor, Pageable pageable);
}
