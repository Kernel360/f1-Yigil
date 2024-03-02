package kr.co.yigil.place.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.bookmark.domain.BookmarkReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceImplTest {

    @Mock PlaceReader placeReader;

    @Mock PlaceCacheReader placeCacheReader;

    @Mock BookmarkReader bookmarkReader;

    @InjectMocks PlaceServiceImpl placeService;

    @DisplayName("getPopularPlace 메서드가 Info 객체의 List를 잘 반환하는지")
    @Test
    void getPopularPlace_ShouldReturnListOfInfo() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        when(placeReader.getPopularPlace()).thenReturn(List.of(mockPlace));

        List<PlaceInfo.Main> popularPlace = placeService.getPopularPlace(mockAccessor);

        assertNotNull(popularPlace);
    }

    @DisplayName("getPlaceInRegion 메서드가 Info 객체의 List를 잘 반환하는지")
    @Test
    void getPlaceInRegion_ShouldReturnListOfInfo() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        when(placeReader.getPlaceInRegion(1L)).thenReturn(List.of(mockPlace));

        List<PlaceInfo.Main> placeInRegion = placeService.getPlaceInRegion(1L, mockAccessor);

        assertNotNull(placeInRegion);
    }

    @DisplayName("retrievePlace 메서드가 Detail 객체를 잘 반환하는지")
    @Test
    void retrievePlace_ShouldReturnDetail() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        when(placeReader.getPlace(1L)).thenReturn(mockPlace);

        PlaceInfo.Detail detail = placeService.retrievePlace(1L, mockAccessor);

        assertNotNull(detail);
    }

    @DisplayName("retrievePlace 메서드가 Detail 객체를 잘 반환하는지")
    @Test
    void findPlace_ShouldReturnDetail() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        when(placeReader.getPlace(1L)).thenReturn(mockPlace);

        PlaceInfo.Detail detail = placeService.retrievePlace(1L, mockAccessor);

        assertNotNull(detail);
    }
}
