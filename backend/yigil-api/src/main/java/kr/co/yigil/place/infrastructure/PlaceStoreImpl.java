package kr.co.yigil.place.infrastructure;

import kr.co.yigil.place.Place;
import kr.co.yigil.place.domain.PlaceStore;
import kr.co.yigil.place.repository.PlaceRepository;
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
