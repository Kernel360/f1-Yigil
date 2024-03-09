package kr.co.yigil.travel.course.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CourseReaderImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseReaderImpl courseReader;

    @DisplayName("courseRepository에서 findAll 호출을 잘 하는지")
    @Test
    void getCourses() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(courseRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(new ArrayList<>()));
        var result = courseReader.getCourses(pageRequest);

        assertThat(result)
            .isNotNull()
            .isInstanceOf(PageImpl.class);
    }

    @DisplayName("courseRepository에서 findById 호출을 잘 하는지")
    @Test
    void getCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(mock(Course.class)));
        var result = courseReader.getCourse(1L);

        assertThat(result)
            .isNotNull()
            .isInstanceOf(Course.class);
    }
}