package kr.co.yigil.place.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import kr.co.yigil.place.domain.PlaceCacheReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlaceCacheStoreImplTest {

    @Mock
    private PlaceCacheReader placeCacheReader;

    @InjectMocks
    private PlaceCacheStoreImpl placeCacheStore;

    @DisplayName("incrementSpotCountInPlace 메서드가 spotCount를 잘 증가시키는지")
    @Test
    void incrementSpotCountInPlace_IncreasesSpotCount() {
        Long placeId = 1L;
        int spotCount = 1;
        when(placeCacheReader.getSpotCount(placeId)).thenReturn(spotCount);

        int result = placeCacheStore.incrementSpotCountInPlace(placeId);

        assertEquals(spotCount + 1, result);
    }

    @DisplayName("decrementSpotCountInPlace 메서드가 spotCount를 잘 감소시키는지")
    @Test
    void decrementSpotCountInPlace_DecreasesSpotCount() {
        Long placeId = 1L;
        int spotCount = 1;
        when(placeCacheReader.getSpotCount(placeId)).thenReturn(spotCount);

        int result = placeCacheStore.decrementSpotCountInPlace(placeId);

        assertEquals(spotCount - 1, result);
    }

    @DisplayName("incrementSpotTotalRateInPlace 메서드가 spotTotalRate를 잘 증가시키는지")
    @Test
    void incrementSpotTotalRateInPlace_IncreasesSpotTotalRate() {
        Long placeId = 1L;
        double spotTotalRate = 1.0;
        when(placeCacheReader.getSpotTotalRate(placeId)).thenReturn(spotTotalRate);

        double result = placeCacheStore.incrementSpotTotalRateInPlace(placeId, 1.0);

        assertEquals(spotTotalRate + 1.0, result);
    }

    @DisplayName("decrementSpotTotalRateInPlace 메서드가 spotTotalRate를 잘 감소시키는지")
    @Test
    void decrementSpotTotalRateInPlace_DecreasesSpotTotalRate() {
        Long placeId = 1L;
        double spotTotalRate = 1.0;
        when(placeCacheReader.getSpotTotalRate(placeId)).thenReturn(spotTotalRate);

        double result = placeCacheStore.decrementSpotTotalRateInPlace(placeId, 1.0);

        assertEquals(spotTotalRate - 1.0, result);
    }
}
