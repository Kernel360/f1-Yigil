package kr.co.yigil.place.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PopularPlace;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PopularPlaceReaderImplTest {

    @Mock
    private PopularPlaceRepository popularPlaceRepository;

    @InjectMocks
    private PopularPlaceReaderImpl popularPlaceReader;

    @DisplayName("getPopularPlace 메서드가 Place의 List를 잘 반환하는지")
    @Test
    void getPopularPlace_ReturnsListOfPlace() {
        PopularPlace popularPlace = mock(PopularPlace.class);
        Place place = mock(Place.class);
        when(popularPlace.getPlace()).thenReturn(place);
        when(popularPlaceRepository.findTop5ByOrderByReferenceCountDesc()).thenReturn(List.of(popularPlace));

        List<Place> result = popularPlaceReader.getPopularPlace();

        assertEquals(result, List.of(place));
    }

    @DisplayName("getPopularPlaceMore 메서드가 Place의 List를 잘 반환하는지")
    @Test
    void getPopularPlaceMore_ReturnsListOfPlace() {
        PopularPlace popularPlace = mock(PopularPlace.class);
        Place place = mock(Place.class);
        when(popularPlace.getPlace()).thenReturn(place);
        when(popularPlaceRepository.findTop20ByOrderByReferenceCountDesc()).thenReturn(List.of(popularPlace));

        List<Place> result = popularPlaceReader.getPopularPlaceMore();

        assertEquals(result, List.of(place));
    }

}
