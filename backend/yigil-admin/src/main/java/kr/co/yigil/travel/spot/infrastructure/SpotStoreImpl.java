package kr.co.yigil.travel.spot.infrastructure;


import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import kr.co.yigil.travel.spot.domain.SpotStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotStoreImpl implements SpotStore {

    private final SpotRepository spotRepository;

    @Override
    public void deleteSpot(Spot spot) {
        spotRepository.delete(spot);
    }
}
