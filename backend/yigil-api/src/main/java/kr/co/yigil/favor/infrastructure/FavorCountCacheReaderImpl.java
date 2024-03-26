package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.FavorCountCacheReader;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FavorCountCacheReaderImpl implements FavorCountCacheReader {

    private final FavorRepository favorRepository;
    @Override
    @Cacheable(value = "favorCount")
    public int getFavorCount(Long travelId) {
        return favorRepository.countByTravelId(travelId);
    }
}
