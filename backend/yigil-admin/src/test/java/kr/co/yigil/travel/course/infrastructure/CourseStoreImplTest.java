package kr.co.yigil.travel.course.infrastructure;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CourseStoreImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseStoreImpl courseStore;

    @DisplayName("코스 삭제시 repository에 delete 호출 확인")
    @Test
    void deleteCourse() {
        Course mock = mock(Course.class);

        courseStore.deleteCourse(mock);
        verify(courseRepository).delete(mock);

    }
}