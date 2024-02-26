package kr.co.yigil.travel.infrastructure.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotReader;
import kr.co.yigil.travel.domain.spot.SpotSeriesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CourseSeriesFactoryImplTest {

    @Mock
    private SpotSeriesFactory spotSeriesFactory;

    @Mock
    private SpotReader spotReader;

    @InjectMocks
    private CourseSeriesFactoryImpl courseSeriesFactory;

    private Course course;
    private Spot spot1, spot2;
    private ModifyCourseRequest modifyCourseRequest;

    @BeforeEach
    void setUp() {
        spot1 = mock(Spot.class);
        when(spot1.getId()).thenReturn(1L);

        spot2 = mock(Spot.class);
        when(spot2.getId()).thenReturn(2L);

        course = mock(Course.class);
        when(course.getSpots()).thenReturn(Arrays.asList(spot1, spot2));
        when(course.getDescription()).thenReturn("New Course Description");
        when(course.getRate()).thenReturn(4.5);

        ModifySpotRequest modifySpotRequest1 = new ModifySpotRequest(1L, 3.0, "수정수정", null, null);
        ModifySpotRequest modifySpotRequest2 = new ModifySpotRequest(2L, 4.0, "수정수정2", null, null);
        List<ModifySpotRequest> modifySpotRequests = Arrays.asList(modifySpotRequest1, modifySpotRequest2);
        List<Long> spotIdOrder = Arrays.asList(2L, 1L);

        modifyCourseRequest = new ModifyCourseRequest("New Course Description", 4.5, spotIdOrder, modifySpotRequests);

        when(spotReader.getSpot(1L)).thenReturn(spot1);
        when(spotReader.getSpot(2L)).thenReturn(spot2);
        when(spotSeriesFactory.modify(any(ModifySpotRequest.class), any(Spot.class)))
                .thenAnswer(invocation -> invocation.getArgument(1));
    }

    @DisplayName("modify 메서드가 Course를 잘 업데이트 하는지")
    @Test
    void modify_CourseIsUpdatedCorrectly() {
        Course modifiedCourse = courseSeriesFactory.modify(modifyCourseRequest, course);

        assertEquals("New Course Description", modifiedCourse.getDescription());
        assertEquals(4.5, modifiedCourse.getRate());

        List<Long> updatedSpotIds = modifiedCourse.getSpots().stream().map(Spot::getId).collect(
                Collectors.toList());
        assertEquals(Arrays.asList(1L, 2L), updatedSpotIds);

        verify(spotSeriesFactory, times(2)).modify(any(ModifySpotRequest.class), any(Spot.class));
        verify(spotReader, times(1)).getSpot(1L);
        verify(spotReader, times(1)).getSpot(2L);
    }
}
