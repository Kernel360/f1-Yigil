package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.FavorCountCacheReader;
import kr.co.yigil.favor.domain.FavorCountCacheStore;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FavorCountCacheStoreImpl implements FavorCountCacheStore {

    private final FavorCountCacheReader favorCountCacheReader;

    @Override
    @CachePut(value = "favorCount")
    public int incrementFavorCount(Long favorId) {
        var favorCount = favorCountCacheReader.getFavorCount(favorId);
        return ++favorCount;
    }

    @Override
    @CachePut(value = "favorCount")
    public int decrementFavorCount(Long favorId) {
        var favorCount = favorCountCacheReader.getFavorCount(favorId);
        return --favorCount;
    }
}
