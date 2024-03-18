package kr.co.yigil.place.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import kr.co.yigil.place.domain.PlaceCacheReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlaceRateCalculatorImplTest {

    @Mock
    private PlaceCacheReader placeCacheReader;

    @InjectMocks
    private PlaceRateCalculatorImpl placeRateCalculator;

    @Test
    void calculatePlaceRate_ReturnsCorrectRate() {
        Long placeId = 1L;
        int spotCount = 2;
        double spotTotalRate = 3.0;
        double expectedPlaceRate = 1.5;

        when(placeCacheReader.getSpotCount(placeId)).thenReturn(spotCount);
        when(placeCacheReader.getSpotTotalRate(placeId)).thenReturn(spotTotalRate);

        double result = placeRateCalculator.calculatePlaceRate(placeId);

        assertEquals(expectedPlaceRate, result);
    }
}