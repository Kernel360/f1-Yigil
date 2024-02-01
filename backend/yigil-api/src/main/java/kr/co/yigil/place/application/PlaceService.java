package kr.co.yigil.place.application;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.dto.request.PlaceDto;
import kr.co.yigil.place.dto.response.PlaceFindResponse;
import kr.co.yigil.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public PlaceFindResponse getPlace(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PLACE_ID));
        return PlaceFindResponse.from(place);
    }

    public Place getOrCreatePlace(String placeName, String placeAddress, String placePointJson) {
        return placeRepository. findByName(placeName)
                .orElseGet(
                        () -> placeRepository.save(PlaceDto.toEntity(
                                placeName,
                                placeAddress,
                                placePointJson)
                        )
                );
    }



}
