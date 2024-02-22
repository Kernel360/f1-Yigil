package kr.co.yigil.travel.infrastructure.spot;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotStore;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotStoreImpl implements SpotStore {
    private final SpotRepository spotRepository;

    @Override
    public Spot store(Spot spot) {
        return spotRepository.save(spot);
    }

    @Override
    public void remove(Spot spot) {
        spotRepository.delete(spot);
    }
}
