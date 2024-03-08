package kr.co.yigil.travel.infrastructure.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@ExtendWith(MockitoExtension.class)
public class CourseReaderImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks CourseReaderImpl courseReader;

    @DisplayName("getCourse 메서드가 Course를 잘 반환하는지")
    @Test
    void getCourse_ReturnsCourse() {
        Long courseId = 1L;
        Course expectedCourse = mock(Course.class);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(expectedCourse));

        Course result = courseReader.getCourse(courseId);

        assertEquals(expectedCourse, result);
    }

    @DisplayName("getCourse 메서드가 Course가 존재하지 않을 때 예외를 잘 발생시키는지")
    @Test
    void getCourse_ThrowsBadRequestException_WhenNotFound() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> courseReader.getCourse(courseId));
    }

    @DisplayName("getCoursesSliceInPlace 메서드가 Course의 Slice를 잘 반환하는지")
    @Test
    void getCoursesSliceInPlace_ReturnsSliceOfCourses() {
        Long placeId = 1L;
        Pageable pageable = mock(Pageable.class);
        Slice<Course> expectedSlice = mock(Slice.class);
        when(courseRepository.findBySpotPlaceId(placeId, pageable)).thenReturn(expectedSlice);

        Slice<Course> result = courseReader.getCoursesSliceInPlace(placeId, pageable);

        assertEquals(expectedSlice, result);
    }

    @DisplayName("searchCourseByPlaceName 메서드가 Course의 Slice를 잘 반환하는지")
    @Test
    void searchCourseByPlaceName_ReturnsSliceOfCourses() {
        String keyword = "keyword";
        Pageable pageable = mock(Pageable.class);
        Slice<Course> expectedSlice = mock(Slice.class);
        when(courseRepository.findByPlaceNameContaining(keyword, pageable)).thenReturn(expectedSlice);

        Slice<Course> result = courseReader.searchCourseByPlaceName(keyword, pageable);

        assertEquals(expectedSlice, result);
    }
}
