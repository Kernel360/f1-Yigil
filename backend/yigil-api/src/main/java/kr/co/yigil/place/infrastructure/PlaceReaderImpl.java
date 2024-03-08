package kr.co.yigil.place.infrastructure;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_PLACE_ID;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.place.domain.DemographicPlace;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand.Coordinate;
import kr.co.yigil.place.domain.PlaceCommand.NearPlaceRequest;
import kr.co.yigil.place.domain.PlaceReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceReaderImpl implements PlaceReader {

    private final PlaceRepository placeRepository;
    private final DemographicPlaceRepository demographicPlaceRepository;

    @Override
    public Optional<Place> findPlaceByNameAndAddress(String placeName, String placeAddress) {
        return placeRepository.findByNameAndAddress(placeName, placeAddress);
    }

    @Override
    public Place getPlace(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_PLACE_ID));
    }

    @Override
    public List<Place> getPopularPlace() {
        return placeRepository.findTop5ByOrderByIdAsc();
    }

    @Override
    public List<Place> getPlaceInRegion(Long regionId) {
        return placeRepository.findTop5ByRegionIdOrderByIdDesc(regionId);
    }

    @Override
    public List<Place> getPlaceInRegionMore(Long regionId) {
        return placeRepository.findTop20ByRegionIdOrderByIdDesc(regionId);
    }

    @Override
    public Page<Place> getNearPlace(NearPlaceRequest command) {
        Coordinate maxCoordinate = command.getMaxCoordinate();
        Coordinate minCoordinate = command.getMinCoordinate();
        PageRequest pageRequest = PageRequest.of(command.getPageNo() - 1, 5, Sort.by("id").descending());
        return placeRepository.findWithinCoordinates(
                minCoordinate.getX(), minCoordinate.getY(),
                maxCoordinate.getX(), maxCoordinate.getY(),
                pageRequest
        );
    }

    @Override
    public List<String> getPlaceKeywords(String keyword) {
        return placeRepository.findTop10ByNameStartingWith(keyword)
                .stream().map(Place::getName).toList();
    }

    @Override
    public List<Place> getPopularPlaceByDemographics(Ages ages, Gender gender) {
        return demographicPlaceRepository.findTop5ByAgesAndGenderOrderByReferenceCountDesc(ages, gender)
                .stream().map(DemographicPlace::getPlace).toList();
    }

    @Override
    public List<Place> getPopularPlaceByDemographicsMore(Ages ages, Gender gender) {
        return demographicPlaceRepository.findTop20ByAgesAndGenderOrderByReferenceCountDesc(ages, gender)
                .stream().map(DemographicPlace::getPlace).toList();
    }

    @Override
    public Slice<Place> getPlacesByKeyword(String keyword, Pageable pageable) {
        return placeRepository.findByNameOrAddressContainingIgnoreCase(keyword, pageable);
    }

}
