package kr.co.yigil.place.infrastructure;

import java.util.Optional;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.domain.PlaceReader;
import kr.co.yigil.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceReaderImpl implements PlaceReader {
    private final PlaceRepository placeRepository;

    @Override
    public Optional<Place> findPlaceByNameAndAddress(String placeName, String placeAddress) {
        return placeRepository.findByNameAndAddress(placeName, placeAddress);
    }
}
