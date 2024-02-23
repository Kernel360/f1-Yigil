package kr.co.yigil.travel.infrastructure.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CourseStoreImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseStoreImpl courseStore;

    @DisplayName("store 메서드가 잘 동작하는지")
    @Test
    void store_SavesAndReturnsCourse() {
        Course initCourse = mock(Course.class);
        when(courseRepository.save(initCourse)).thenReturn(initCourse);

        Course savedCourse = courseStore.store(initCourse);

        assertEquals(initCourse, savedCourse);
        verify(courseRepository).save(initCourse);
    }

    @DisplayName("remove 메서드가 잘 동작하는지")
    @Test
    void remove_DeletesCourse() {
        Course course = mock(Course.class);

        courseStore.remove(course);

        verify(courseRepository).delete(course);
    }
}
