package kr.co.yigil.place.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import kr.co.yigil.travel.domain.spot.SpotReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlaceCacheReaderImplTest {

    @Mock
    private SpotReader spotReader;

    @InjectMocks
    private PlaceCacheReaderImpl placeCacheReader;

    @DisplayName("getSpotCount 메서드가 spot의 Count를 잘 반환하는지")
    @Test
    void getSpotCount_ReturnsSpotCount() {
        Long placeId = 1L;
        int expectedSpotCount = 1;
        when(spotReader.getSpotCountInPlace(placeId)).thenReturn(expectedSpotCount);

        int result = placeCacheReader.getSpotCount(placeId);

        assertEquals(expectedSpotCount, result);
    }
}
