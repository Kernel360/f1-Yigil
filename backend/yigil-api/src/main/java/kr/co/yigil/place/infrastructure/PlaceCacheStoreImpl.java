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
}
