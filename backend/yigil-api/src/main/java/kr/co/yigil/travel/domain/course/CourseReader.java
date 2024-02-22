package kr.co.yigil.travel.domain.course;

import kr.co.yigil.travel.domain.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CourseReader {

    Slice<Course> getCoursesSliceInPlace(Long placeId, Pageable pageable);
}
