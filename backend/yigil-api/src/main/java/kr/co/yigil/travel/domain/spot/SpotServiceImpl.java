package kr.co.yigil.travel.domain.spot;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.favor.domain.FavorReader;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.follow.domain.FollowReader;
import kr.co.yigil.global.Selected;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCacheStore;
import kr.co.yigil.place.domain.PlaceReader;
import kr.co.yigil.place.domain.PlaceStore;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterPlaceRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.domain.spot.SpotInfo.Main;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpot;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpotsResponse;
import kr.co.yigil.travel.domain.spot.SpotInfo.Slice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {

    private final MemberReader memberReader;
    private final SpotReader spotReader;
    private final PlaceReader placeReader;
    private final FavorReader favorReader;
    private final FollowReader followReader;
    private final SpotStore spotStore;
    private final PlaceStore placeStore;
    private final PlaceCacheStore placeCacheStore;

    private final SpotSeriesFactory spotSeriesFactory;
    private final FileUploader fileUploader;

    @Override
    @Transactional(readOnly = true)
    public Slice getSpotSliceInPlace(final Long placeId, final Accessor accessor, final Pageable pageable) {
        var slice = spotReader.getSpotSliceInPlace(placeId, pageable);
        var mains = slice.getContent()
                .stream()
                .map(spot -> {
                    boolean isLiked = accessor.isMember() && favorReader.existsByMemberIdAndTravelId(accessor.getMemberId(),
                            spot.getId());
                    boolean isFollowing = followReader.isFollowing(accessor.getMemberId(), spot.getMember().getId());
                    return new Main(spot, isLiked, isFollowing);
                }).collect(Collectors.toList());
        return new Slice(mains, slice.hasNext());
    }

    @Override
    @Transactional(readOnly = true)
    public MySpot retrieveMySpotInfoInPlace(Long placeId, Long memberId) {
        var spotOptional = spotReader.findSpotByPlaceIdAndMemberId(placeId, memberId);
        return new MySpot(spotOptional);
    }

    @Override
    @Transactional
    public void registerSpot(RegisterSpotRequest command, Long memberId) {
        Member member = memberReader.getMember(memberId);
        Optional<Place> optionalPlace = placeReader.findPlaceByNameAndAddress(
                command.getRegisterPlaceRequest().getPlaceName(), command.getRegisterPlaceRequest().getPlaceAddress());
        if (optionalPlace.isPresent() && spotReader.isExistPlace(memberId, optionalPlace.get().getId())) {
            throw new BadRequestException(ExceptionCode.SPOT_ALREADY_EXIST_IN_PLACE);
        }

        var attachFiles = spotSeriesFactory.initAttachFiles(command);
        Place place = optionalPlace.orElseGet(() -> registerNewPlace(command.getRegisterPlaceRequest(), attachFiles.getFiles().getFirst()));
        placeCacheStore.incrementSpotCountInPlace(place.getId());
        placeCacheStore.incrementSpotTotalRateInPlace(place.getId(), command.getRate());
        spotStore.store(command.toEntity(member, place, false, attachFiles));
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
        if (!Objects.equals(spot.getMember().getId(), memberId))
            throw new AuthException(ExceptionCode.INVALID_AUTHORITY);
        spotSeriesFactory.modify(command, spot);
    }

    @Override
    @Transactional
    public void deleteSpot(Long spotId, Long memberId) {
        var spot = spotReader.getSpot(spotId);
        if (!Objects.equals(spot.getMember().getId(), memberId)) throw new AuthException(
                ExceptionCode.INVALID_AUTHORITY);
        if (spot.getPlace() != null) {
            placeCacheStore.decrementSpotCountInPlace(spot.getPlace().getId());
            placeCacheStore.decrementSpotTotalRateInPlace(spot.getPlace().getId(), spot.getRate());
        }
        spotStore.remove(spot);
    }

    private Place registerNewPlace(RegisterPlaceRequest command, AttachFile placeImageFile) {
        var mapStaticImage = fileUploader.upload(command.getMapStaticImageFile());
        return placeStore.store(command.toEntity(placeImageFile, mapStaticImage));
    }

    @Override
    @Transactional
    public MySpotsResponse retrieveSpotList(Long memberId, Selected visibility, Pageable pageable) {
        var pageSpot = spotReader.getMemberSpotList(memberId, visibility, pageable);
        List<SpotInfo.SpotListInfo> spotInfoList = pageSpot.getContent().stream()
                .map(SpotInfo.SpotListInfo::new)
                .toList();
        return new MySpotsResponse(spotInfoList, pageSpot.getTotalPages());
    }

    @Override
    public CourseInfo.MySpotsInfo getMySpotsDetailInfo(List<Long> spotIds, Long memberId) {
        for (Long spotId : spotIds) {
            if (!spotReader.isExistSpot(spotId, memberId)) {
                throw new BadRequestException(ExceptionCode.INVALID_AUTHORITY);
            }
        }
        var spots = spotReader.getSpots(spotIds);
        return new CourseInfo.MySpotsInfo(spots);
    }

    @Override
    @Transactional(readOnly = true)
    public SpotInfo.MyFavoriteSpotsInfo getFavoriteSpotsInfo(Long memberId, Pageable pageRequest) {
        var sliceSpot = spotReader.getFavoriteSpotList(memberId, pageRequest);
        List<SpotInfo.FavoriteSpotInfo> spotInfoList = sliceSpot.getContent().stream()
                .map(spot -> {
                            boolean isFollowing = followReader.isFollowing(memberId, spot.getMember().getId());
                            return new SpotInfo.FavoriteSpotInfo(spot, isFollowing);
                        }

                )
                .toList();
        return new SpotInfo.MyFavoriteSpotsInfo(spotInfoList, sliceSpot.hasNext());
    }
}