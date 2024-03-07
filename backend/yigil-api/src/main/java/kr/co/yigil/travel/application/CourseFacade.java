package kr.co.yigil.travel.application;

import kr.co.yigil.file.FileUploader;
import kr.co.yigil.global.Selected;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequestWithSpotInfo;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.domain.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseFacade {
    private final CourseService courseService;
    private final FileUploader fileUploader;

    public Slice<Course> getCourseSliceInPlace(Long placeId, Pageable pageable) {
        return courseService.getCoursesSliceInPlace(placeId, pageable);
    }

    public void registerCourse(RegisterCourseRequest command, Long memberId) {
        courseService.registerCourse(command, memberId);
    }

    public void registerCourseWithoutSeries(RegisterCourseRequestWithSpotInfo command, Long memberId) {
        courseService.registerCourseWithoutSeries(command, memberId);
    }

    public CourseInfo.Main retrieveCourseInfo(Long courseId) { return courseService.retrieveCourseInfo(courseId); }

    public void modifyCourse(ModifyCourseRequest command, Long courseId, Long memberId) {
        courseService.modifyCourse(command, courseId, memberId);
    }

    public void deleteCourse(Long courseId, Long memberId) { courseService.deleteCourse(courseId, memberId); }

    public CourseInfo.MyCoursesResponse getMemberCoursesInfo(final Long memberId,
        Pageable pageable, Selected selected) {
        return courseService.retrieveCourseList(memberId, pageable, selected);
    }

//    public Slice searchCourseByPlaceName(String keyword, Pageable pageable) {
//        return courseService.searchCourseByPlaceName(keyword, pageable);
//    }
}
