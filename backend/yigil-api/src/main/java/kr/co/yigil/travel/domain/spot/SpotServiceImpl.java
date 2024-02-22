package kr.co.yigil.travel.domain.spot;

import java.util.Objects;
import java.util.Optional;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.domain.PlaceCacheStore;
import kr.co.yigil.place.domain.PlaceReader;
import kr.co.yigil.place.domain.PlaceStore;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterPlaceRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.domain.spot.SpotInfo.Main;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {
    private final MemberReader memberReader;
    private final SpotReader spotReader;
    private final PlaceReader placeReader;

    private final SpotStore spotStore;
    private final PlaceStore placeStore;
    private final PlaceCacheStore placeCacheStore;

    private final SpotSeriesFactory spotSeriesFactory;
    private final FileUploader fileUploader;
    @Override
    @Transactional(readOnly = true)
    public Slice<Spot> getSpotSliceInPlace(Long placeId, Pageable pageable) {
        return spotReader.getSpotSliceInPlace(placeId, pageable);
    }

    @Override
    @Transactional
    public void registerSpot(RegisterSpotRequest command, Long memberId) {
        Member member = memberReader.getMember(memberId);
        Optional<Place> optionalPlace = placeReader.findPlaceByNameAndAddress(command.getRegisterPlaceRequest().getPlaceName(), command.getRegisterPlaceRequest().getPlaceAddress());
        Place place = optionalPlace.orElseGet(()-> registerNewPlace(command.getRegisterPlaceRequest()));
        var spot = spotStore.store(command.toEntity(member, place, false));
        var spotCount = placeCacheStore.incrementSpotCountInPlace(place.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Main retrieveSpotInfo(Long spotId) {
        var spot = spotReader.getSpot(spotId);
        return new Main(spot);
    }

    @Override
    @Transactional
    public void modifySpot(ModifySpotRequest command, Long spotId, Long memberId) {
        var spot = spotReader.getSpot(spotId);
        if(!Objects.equals(spot.getMember().getId(), memberId)) throw new AuthException(ExceptionCode.INVALID_AUTHORITY);
        var modifiedSpot = spotSeriesFactory.modify(command, spot);
    }

    @Override
    @Transactional
    public void deleteSpot(Long spotId, Long memberId) {
        var spot = spotReader.getSpot(spotId);
        if(!Objects.equals(spot.getMember().getId(), memberId)) throw new AuthException(
                ExceptionCode.INVALID_AUTHORITY);
        spotStore.remove(spot);
        if(spot.getPlace() != null) placeCacheStore.decrementSpotCountInPlace(spot.getPlace().getId());
    }

    @Override
    public Page<Spot> getSpotListByMemberId(Long memberId, Pageable pageable) {
        return spotReader.getSpotSliceByMemberId(memberId, pageable);
    }

    private Place registerNewPlace(RegisterPlaceRequest command) {
        fileUploader.upload(command.getPlaceImageFile());
        fileUploader.upload(command.getMapStaticImageFile());
        return placeStore.store(command.toEntity());
    }
}
