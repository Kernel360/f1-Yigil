package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.FavorReader;
import kr.co.yigil.favor.domain.repository.FavorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavorReaderImpl implements FavorReader {

    private final FavorRepository favorRepository;

    public int getFavorCount(Long travelId) {
        return favorRepository.countByTravelId(travelId);
    }
}
