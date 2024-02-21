package kr.co.yigil.travel.application;

import kr.co.yigil.travel.domain.spot.SpotCount;
import kr.co.yigil.travel.domain.repository.SpotCountRepository;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpotRedisIntegrityService {
    private final SpotRepository spotRepository;
    private final SpotCountRepository spotCountRepository;

    @Transactional
    public SpotCount ensureSpotCounts(Long placeId) {
        return spotCountRepository.findByPlaceId(placeId)
                .orElseGet(() -> {
                    SpotCount spotCount = new SpotCount(placeId, spotRepository.countByPlaceId(placeId));
                    spotCountRepository.save(spotCount);
                    return spotCount;
                });
    }
}
