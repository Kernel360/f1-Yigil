package kr.co.yigil.place.infrastructure;

import kr.co.yigil.place.domain.PlaceCacheReader;
import kr.co.yigil.travel.domain.spot.SpotReader;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceCacheReaderImpl implements PlaceCacheReader {

    private final SpotReader spotReader;

    @Override
    //@Cacheable(value = "spotCount")
    public int getSpotCount(Long placeId) {
        return spotReader.getSpotCountInPlace(placeId);
    }

    @Override
    //@Cacheable(value = "spotTotalRate")
    public double getSpotTotalRate(Long placeId) {
        return spotReader.getSpotTotalRateInPlace(placeId);
    }

}
