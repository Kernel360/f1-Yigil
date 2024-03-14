package kr.co.yigil.place.infrastructure;

import kr.co.yigil.place.domain.PlaceCacheReader;
import kr.co.yigil.place.domain.PlaceRateCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceRateCaculatorImpl implements PlaceRateCalculator {
    private final PlaceCacheReader placeCacheReader;

    @Override
    public double calculatePlaceRate(Long placeId) {
        int spotCount = placeCacheReader.getSpotCount(placeId);
        double spotTotalRate = placeCacheReader.getSpotTotalRate(placeId);
        return Math.round((spotTotalRate / spotCount) * 10.0) / 10.0;
    }

}
