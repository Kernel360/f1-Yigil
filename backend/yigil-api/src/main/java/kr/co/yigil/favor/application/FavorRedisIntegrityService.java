package kr.co.yigil.favor.application;

import jakarta.transaction.Transactional;
import java.util.Optional;
import kr.co.yigil.favor.domain.FavorCount;
import kr.co.yigil.favor.domain.repository.FavorCountRepository;
import kr.co.yigil.favor.domain.repository.FavorRepository;
import kr.co.yigil.travel.Travel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavorRedisIntegrityService {
    private final FavorRepository favorRepository;
    private final FavorCountRepository favorCountRepository;

    @Transactional
    public FavorCount ensureFavorCounts(Travel travel) {
        Long travelId = travel.getId();
        Optional<FavorCount> existingFavorCount = favorCountRepository.findByTravelId(travelId);
        if (existingFavorCount.isPresent()) {
            return existingFavorCount.get();
        } else {
            FavorCount favorCount = getFavorCount(travel);
            favorCountRepository.save(favorCount);
            return favorCount;
        }
    }

    private FavorCount getFavorCount(Travel travel) {
        return new FavorCount(travel.getId(), favorRepository.countByTravelId(travel.getId()));
    }
}
