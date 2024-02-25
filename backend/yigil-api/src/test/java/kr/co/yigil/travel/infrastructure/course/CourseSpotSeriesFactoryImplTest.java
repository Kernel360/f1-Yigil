package kr.co.yigil.travel.infrastructure.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.domain.PlaceCacheStore;
import kr.co.yigil.place.domain.PlaceReader;
import kr.co.yigil.place.domain.PlaceStore;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequestWithSpotInfo;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterPlaceRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.domain.spot.SpotReader;
import kr.co.yigil.travel.domain.spot.SpotStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class CourseSpotSeriesFactoryImplTest {

    @Mock
    private MemberReader memberReader;
    @Mock
    private PlaceReader placeReader;
    @Mock
    private SpotReader spotReader;

    @Mock
    private PlaceStore placeStore;
    @Mock
    private SpotStore spotStore;
    @Mock
    private PlaceCacheStore placeCacheStore;

    @Mock
    private FileUploader fileUploader;

    @InjectMocks
    private CourseSpotSeriesFactoryImpl courseSpotSeriesFactory;

    private RegisterCourseRequest request;
    private Member member;
    private Place place;
    private Spot spot;

    @BeforeEach
    void setUp() {
        member = mock(Member.class);
        place = mock(Place.class);
        spot = mock(Spot.class);
        request = mock(RegisterCourseRequest.class);
    }

    @DisplayName("store 메서드가 존재하는 spot에 대해서 Course Series를 잘 동작하는지")
    @Test
    void store_WithExistingSpots_RegistersSpotsSuccessfully() {
        RegisterCourseRequestWithSpotInfo requestWithSpotInfo = mock(RegisterCourseRequestWithSpotInfo.class); // 필요한 메서드를 모킹
        when(requestWithSpotInfo.getSpotIds()).thenReturn(Arrays.asList(1L, 2L));
        when(spotReader.getSpots(anyList())).thenReturn(Collections.singletonList(spot));

        List<Spot> resultSpots = courseSpotSeriesFactory.store(requestWithSpotInfo, 1L);

        assertFalse(resultSpots.isEmpty());
        verify(spotReader).getSpots(anyList());
        verify(spot, times(1)).changeInCourse();
    }
}
