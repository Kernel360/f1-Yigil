package kr.co.yigil.place.infrastructure;

import kr.co.yigil.place.domain.PlaceCacheReader;
import kr.co.yigil.place.domain.PlaceCacheStore;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceCacheStoreImpl implements PlaceCacheStore {

    private final PlaceCacheReader placeCacheReader;

    @Override
    @CachePut(value = "spotCount")
    public int incrementSpotCountInPlace(Long placeId) {
        int spotCount = placeCacheReader.getSpotCount(placeId);
        return ++spotCount;
    }

    @Override
    @CachePut(value = "spotCount")
    public int decrementSpotCountInPlace(Long placeId) {
        int spotCount = placeCacheReader.getSpotCount(placeId);
        return --spotCount;
    }

    @Override
    @CachePut(value = "spotTotalRate", key = "#placeId")
    public double incrementSpotTotalRateInPlace(Long placeId, double rate) {
        double spotTotalRate = placeCacheReader.getSpotTotalRate(placeId);
        return spotTotalRate + rate;
    }

    @Override
    @CachePut(value = "spotTotalRate", key = "#placeId")
    public double decrementSpotTotalRateInPlace(Long placeId, double rate) {
        double spotTotalRate = placeCacheReader.getSpotTotalRate(placeId);
        return spotTotalRate - rate;
    }
}
