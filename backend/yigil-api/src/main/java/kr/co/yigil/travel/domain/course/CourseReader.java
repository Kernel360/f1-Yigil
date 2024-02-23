package kr.co.yigil.travel.domain.course;

import kr.co.yigil.travel.domain.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import kr.co.yigil.member.domain.MemberInfo;

public interface CourseReader {
    Course getCourse(Long courseId);
    Slice<Course> getCoursesSliceInPlace(Long placeId, Pageable pageable);
    MemberInfo.MemberCourseResponse findAllByMemberId(Long memberId, Pageable pageable, String selectInfo);
}

