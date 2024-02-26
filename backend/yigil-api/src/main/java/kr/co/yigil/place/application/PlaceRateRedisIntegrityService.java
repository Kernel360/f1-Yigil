package kr.co.yigil.place.application;

import kr.co.yigil.place.domain.PlaceRate;
import kr.co.yigil.place.domain.repository.PlaceRateRepository;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceRateRedisIntegrityService {

    private final PlaceRateRepository placeRateRepository;
    private final SpotRepository spotRepository;

    @Transactional
    public PlaceRate ensurePlaceRate(Long placeId) {
        return placeRateRepository.findByPlaceId(placeId)
                .orElseGet(() -> {
                    Double totalRateByPlaceId = spotRepository.findTotalRateByPlaceId(placeId)
                            .orElse(0.0);
                    PlaceRate placeRate = new PlaceRate(placeId, totalRateByPlaceId);
                    return placeRateRepository.save(placeRate);
                });
    }

}
