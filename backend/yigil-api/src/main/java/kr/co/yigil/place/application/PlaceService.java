package kr.co.yigil.place.application;

import kr.co.yigil.file.AttachFile;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.dto.request.PlaceDto;
import kr.co.yigil.place.dto.response.PlaceInfoResponse;
import kr.co.yigil.place.dto.response.PlaceMapStaticImageResponse;
import kr.co.yigil.place.repository.PlaceRepository;
import kr.co.yigil.travel.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final SpotRepository spotRepository;

    @Transactional(readOnly = true)
    public PlaceInfoResponse getPlaceInfo(Long placeId) {
        Place place = getPlaceById(placeId);
        return PlaceInfoResponse.from(place);
    }

    @Transactional(readOnly = true)
    public PlaceMapStaticImageResponse getPlaceStaticImage(String uniquePlaceId) {
        Place place = placeRepository.findByUniquePlaceId(uniquePlaceId)
                .orElseThrow( () -> new BadRequestException(ExceptionCode.NOT_FOUND_PLACE_ID));
        return PlaceMapStaticImageResponse.from(place);
    }

    public Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PLACE_ID));
    }

    public Place getOrCreatePlace(String uniquePlaceId, String placeName, String placeAddress, String placePointJson, String placeImageUrl, AttachFile mapStaticImageFile) {
        return placeRepository. findByUniquePlaceId(uniquePlaceId)
                .orElseGet(
                        () -> placeRepository.save(PlaceDto.toEntity(
                                uniquePlaceId,
                                placeName,
                                placeAddress,
                                placePointJson,
                                placeImageUrl,
                                mapStaticImageFile
                                )
                        )
                );
    }



}
