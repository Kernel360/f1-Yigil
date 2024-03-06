package kr.co.yigil.place.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @DisplayName("getPlaceInRegionMore 메서드가 Place의 리스트를 잘 반환하는지")
    @Test
    void getPlaceInRegionMore_ReturnsListOfPlace() {
        Long regionId = 1L;
        Place place1 = mock(Place.class);
        Place place2 = mock(Place.class);
        Place place3 = mock(Place.class);
        Place place4 = mock(Place.class);
        Place place5 = mock(Place.class);
        Place place6 = mock(Place.class);
        Place place7 = mock(Place.class);
        Place place8 = mock(Place.class);
        Place place9 = mock(Place.class);
        Place place10 = mock(Place.class);
        Place place11 = mock(Place.class);
        Place place12 = mock(Place.class);
        Place place13 = mock(Place.class);
        Place place14 = mock(Place.class);
        Place place15 = mock(Place.class);
        Place place16 = mock(Place.class);
        Place place17 = mock(Place.class);
        Place place18 = mock(Place.class);
        Place place19 = mock(Place.class);
        Place place20 = mock(Place.class);
        when(placeRepository.findTop20ByRegionIdOrderByIdDesc(regionId)).thenReturn(
                Arrays.asList(place1, place2, place3, place4, place5, place6, place7, place8, place9, place10,
                        place11, place12, place13, place14, place15, place16, place17, place18, place19, place20));

        assertEquals(placeReader.getPlaceInRegionMore(regionId).size(), 20);
    }

    @DisplayName("getNearPlace 메서드가 Page<Place>를 잘 반환하는지")
    @Test
    void getNearPlace_ReturnsPageOfPlace() {
        PlaceCommand.NearPlaceRequest command = mock(PlaceCommand.NearPlaceRequest.class);
        when(command.getPageNo()).thenReturn(1);
        PlaceCommand.Coordinate mockCoordinate = mock(PlaceCommand.Coordinate.class);
        when(mockCoordinate.getX()).thenReturn(1.0);
        when(mockCoordinate.getY()).thenReturn(1.0);
        when(command.getMinCoordinate()).thenReturn(mockCoordinate);
        when(command.getMaxCoordinate()).thenReturn(mockCoordinate);

        Page<Place> mockPage = mock(Page.class);

        when(placeRepository.findWithinCoordinates(
                anyDouble(), anyDouble(), anyDouble(), anyDouble(), any(Pageable.class))).thenReturn(mockPage);

        var result = placeReader.getNearPlace(command);

        assertEquals(result, mockPage);
    }
}
