package kr.co.yigil.place.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.place.domain.Place;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlaceReaderImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceReaderImpl placeReader;

    @DisplayName("findByNameAndAddress 메서드가 Place의 Optional 객체를 잘 반환하는지")
    @Test
    void findByNameAndAddress_ReturnsOptionalOfPlace() {
        String placeName = "name";
        String address = "address";
        Place expectedPlace = mock(Place.class);
        when(placeRepository.findByNameAndAddress(placeName, address)).thenReturn(
                Optional.of(expectedPlace));

        Optional<Place> result = placeReader.findPlaceByNameAndAddress(placeName, address);

        assert (result.isPresent());
        assertEquals(result.get(), expectedPlace);
    }

    @DisplayName("getPlace 메서드가 Place을 잘 반환하는지")
    @Test
    void getPlace_ReturnsPlace() {
        Long placeId = 1L;
        Place expectedPlace = mock(Place.class);
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(expectedPlace));

        Place result = placeReader.getPlace(placeId);

        assertEquals(expectedPlace, result);
    }

    @DisplayName("getPlace 메서드가 placeId가 유효하지 않을 때 예외를 잘 발생시키는지")
    @Test
    void getPlace_ThrowsBadRequestException_WhenNotFound() {
        Long placeId = 1L;
        when(placeRepository.findById(placeId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> placeReader.getPlace(placeId));
    }

    @DisplayName("getPopularPlace 메서드가 Place의 리스트를 잘 반환하는지")
    @Test
    void getPopularPlace_ReturnsListOfPlace() {
        Place place1 = mock(Place.class);
        Place place2 = mock(Place.class);
        Place place3 = mock(Place.class);
        Place place4 = mock(Place.class);
        Place place5 = mock(Place.class);
        when(placeRepository.findTop5ByOrderByIdAsc()).thenReturn(
                Arrays.asList(place1, place2, place3, place4, place5));

        assertEquals(placeReader.getPopularPlace().size(), 5);
    }

    @DisplayName("getPlaceInRegion 메서드가 Place의 리스트를 잘 반환하는지")
    @Test
    void getPlaceInRegion_ReturnsListOfPlace() {
        Long regionId = 1L;
        Place place1 = mock(Place.class);
        Place place2 = mock(Place.class);
        Place place3 = mock(Place.class);
        Place place4 = mock(Place.class);
        Place place5 = mock(Place.class);
        when(placeRepository.findTop5ByRegionIdOrderByIdDesc(regionId)).thenReturn(
                Arrays.asList(place1, place2, place3, place4, place5));

        assertEquals(placeReader.getPlaceInRegion(regionId).size(), 5);
    }
}
