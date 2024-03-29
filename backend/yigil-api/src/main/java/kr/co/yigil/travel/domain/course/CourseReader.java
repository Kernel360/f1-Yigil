package kr.co.yigil.travel.domain.course;

import kr.co.yigil.global.Selected;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.dto.CourseListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CourseReader {
    Course getCourse(Long courseId);

    Slice<Course> getCoursesSliceInPlace(Long placeId, Pageable pageable);

    Page<CourseListDto> getMemberCourseList(Long memberId, Pageable pageable, Selected selectInfo);

    Slice<Course> searchCourseByPlaceName(String keyword, Pageable pageable);

    Slice<Course> getFavoriteCourses(Long memberId, Pageable pageRequest);
}

