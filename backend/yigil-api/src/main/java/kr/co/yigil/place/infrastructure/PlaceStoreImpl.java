package kr.co.yigil.place.infrastructure;

import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceStoreImpl implements PlaceStore {
    private final PlaceRepository placeRepository;

    @Override
    public Place store(Place initPlace) {
        return placeRepository.save(initPlace);
    }
}
