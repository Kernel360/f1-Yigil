package kr.co.yigil.place.application;

import kr.co.yigil.file.AttachFile;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.dto.request.PlaceDto;
import kr.co.yigil.place.dto.response.PlaceFindDto;
import kr.co.yigil.place.dto.response.PlaceInfoResponse;
import kr.co.yigil.place.dto.response.PlaceMapStaticImageResponse;
import kr.co.yigil.place.dto.response.RateResponse;
import kr.co.yigil.place.repository.PlaceRepository;
import kr.co.yigil.travel.application.SpotRedisIntegrityService;
import kr.co.yigil.travel.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final SpotRepository spotRepository;
    private final SpotRedisIntegrityService spotRedisIntegrityService;
    private final PlaceRateRedisIntegrityService placeRateRedisIntegrityService;

    @Transactional(readOnly = true)
    public PlaceInfoResponse getPlaceInfo(Long placeId) {
        Place place = getPlaceById(placeId);
        int spotCount = spotRedisIntegrityService.ensureSpotCounts(placeId).getSpotCount();

        return PlaceInfoResponse.from(place, spotCount);
    }

    @Transactional(readOnly = true)
    public RateResponse getMemberRate(Long placeId, Long memberId) {
        return spotRepository.findByPlaceIdAndMemberId(placeId, memberId)
                .map(spot -> new RateResponse(spot.getRate()))
                .orElse(new RateResponse(null));
    }

    @Transactional(readOnly = true)
    public PlaceMapStaticImageResponse getPlaceStaticImage(String name, String address) {
        Place place = placeRepository.findByNameAndAddress(name, address)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PLACE_ID));
        return PlaceMapStaticImageResponse.from(place);
    }

    public Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_PLACE_ID));
    }

    public Place getOrCreatePlace(String placeName, String placeAddress, String placePointJson,
            String placeImageUrl, AttachFile mapStaticImageFile) {
        return placeRepository.findByNameAndAddress(placeName, placeAddress)
                .orElseGet(
                        () -> placeRepository.save(PlaceDto.toEntity(
                                        placeName,
                                        placeAddress,
                                        placePointJson,
                                        placeImageUrl,
                                        mapStaticImageFile
                                )
                        )
                );
    }

    public Slice<PlaceFindDto> getPlaceList(PageRequest pageRequest) {
        return placeRepository.findAll(pageRequest)
                .map(this::getPlaceFindDto);
    }
}
