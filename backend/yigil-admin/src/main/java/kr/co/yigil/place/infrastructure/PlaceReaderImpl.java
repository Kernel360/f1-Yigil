package kr.co.yigil.place.infrastructure;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_PLACE_ID;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceReaderImpl implements PlaceReader {
    private final PlaceRepository placeRepository;

    @Override
    public List<Place> getPlaces(double startX, double startY, double endX, double endY) {
        return placeRepository.findWithinCoordinates(startX, startY, endX, endY);
    }

    @Override
    public Place getPlace(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(()-> new BadRequestException(NOT_FOUND_PLACE_ID));
    }
}
