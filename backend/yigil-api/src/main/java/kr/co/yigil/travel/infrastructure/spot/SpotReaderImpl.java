package kr.co.yigil.travel.infrastructure.spot;

import kr.co.yigil.global.Selected;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.dto.SpotListDto;
import kr.co.yigil.travel.domain.spot.SpotReader;
import kr.co.yigil.travel.infrastructure.SpotQueryDslRepository;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_SPOT_ID;

@Component
@RequiredArgsConstructor
public class SpotReaderImpl implements SpotReader {

    private final SpotRepository spotRepository;
    private final SpotQueryDslRepository spotQueryDslRepository;

    @Override
    public Spot getSpot(Long spotId) {
        return spotRepository.findById(spotId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_SPOT_ID));
    }

    @Override
    public Optional<Spot> findSpotByPlaceIdAndMemberId(Long placeId, Long memberId) {
        return spotRepository.findTopByPlaceIdAndMemberId(placeId, memberId);
    }

    @Override
    public List<Spot> getSpots(List<Long> spotIds) {
        return spotIds.stream()
                .map(this::getSpot)
                .collect(toList());
    }

    @Override
    public Slice<Spot> getSpotSliceInPlace(Long placeId, Pageable pageable) {
        return spotRepository.findAllByPlaceIdAndIsPrivateIsFalse(placeId,
                pageable);
    }

    @Override
    public int getSpotCountInPlace(Long placeId) {
        return spotRepository.countByPlaceId(placeId);
    }

    @Override
    public Page<Spot> getSpotSliceByMemberId(Long memberId, Pageable pageable) {
        return spotRepository.findAllByMemberIdAndIsInCourseIsFalse(memberId, pageable);
    }

    @Override
    public Page<SpotListDto> getMemberSpotList(Long memberId, Selected visibility, Pageable pageable
    ) {
        return spotQueryDslRepository.findAllByMemberIdAndIsPrivate(memberId, visibility, pageable);
    }

    @Override
    public boolean isExistPlace(Long placeId, Long memberId) {
        return spotRepository.existsByPlaceIdAndMemberId(placeId, memberId);
    }

    @Override
    public double getSpotTotalRateInPlace(Long placeId) {
        return spotRepository.getRateTotalByPlaceId(placeId).orElse(0.0);
    }

    @Override
    public boolean isExistSpot(Long spotId, Long memberId) {
        return spotRepository.existsByIdAndMemberId(spotId, memberId);
    }

    @Override
    public List<Spot> getMemberSpots(Long memberId, List<Long> spotIds) {
        return spotIds.stream().map(
                spotId -> spotRepository.findByIdAndMemberId(spotId, memberId)
                        .orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_AUTHORITY))
        ).toList();
    }

    @Override
    public List<Long> getMySpotPlaceIds(Long memberId) {
        return spotRepository.findPlaceIdByMemberId(memberId);
    }

    @Override
    public Page<Spot> getFavoriteSpotList(Long memberId, Pageable pageRequest) {
        return spotRepository.findAllMembersFavoriteSpot(memberId, pageRequest);
    }
}
