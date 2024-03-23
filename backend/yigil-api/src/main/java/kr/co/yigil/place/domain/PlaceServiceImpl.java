package kr.co.yigil.place.domain;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.bookmark.domain.BookmarkReader;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.place.domain.PlaceCommand.NearPlaceRequest;
import kr.co.yigil.place.domain.PlaceInfo.Detail;
import kr.co.yigil.place.domain.PlaceInfo.Keyword;
import kr.co.yigil.place.domain.PlaceInfo.Main;
import kr.co.yigil.place.domain.PlaceInfo.MapStaticImageInfo;
import kr.co.yigil.travel.domain.spot.SpotReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceReader placeReader;
    private final PopularPlaceReader popularPlaceReader;
    private final PlaceCacheReader placeCacheReader;
    private final BookmarkReader bookmarkReader;
    private final MemberReader memberReader;
    private final SpotReader spotReader;
    private final PlaceRateCalculator placeRateCalculator;

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPopularPlace(final Accessor accessor) {
        return popularPlaceReader.getPopularPlace().stream()
                .map(place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    double placeRate = placeRateCalculator.calculatePlaceRate(place.getId());
                    if (accessor.isMember()) {
                        boolean isBookmarked = bookmarkReader.isBookmarked(accessor.getMemberId(), place.getId());
                        return new Main(place, spotCount, isBookmarked, placeRate);
                    }
                    return new Main(place, spotCount, placeRate);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPopularPlaceMore(final Accessor accessor) {
        return popularPlaceReader.getPopularPlaceMore().stream()
                .map(place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    double placeRate = placeRateCalculator.calculatePlaceRate(place.getId());
                    if (accessor.isMember()) {
                        boolean isBookmarked = bookmarkReader.isBookmarked(accessor.getMemberId(), place.getId());
                        return new Main(place, spotCount, isBookmarked, placeRate);
                    }
                    return new Main(place, spotCount, placeRate);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPlaceInRegion(final Long regionId, final Accessor accessor) {
        return placeReader.getPlaceInRegion(regionId).stream()
                .map(place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    double placeRate = placeRateCalculator.calculatePlaceRate(place.getId());
                    if (accessor.isMember()) {
                        boolean isBookmarked = bookmarkReader.isBookmarked(accessor.getMemberId(), place.getId());
                        return new Main(place, spotCount, isBookmarked, placeRate);
                    }
                    return new Main(place, spotCount, placeRate);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPlaceInRegionMore(Long regionId, Accessor accessor) {
        return placeReader.getPlaceInRegionMore(regionId).stream()
                .map(place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    double placeRate = placeRateCalculator.calculatePlaceRate(place.getId());
                    if (accessor.isMember()) {
                        boolean isBookmarked = bookmarkReader.isBookmarked(accessor.getMemberId(), place.getId());
                        return new Main(place, spotCount, isBookmarked, placeRate);
                    }
                    return new Main(place, spotCount, placeRate);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Detail retrievePlace(final Long placeId, final Accessor accessor) {
        var place = placeReader.getPlace(placeId);
        int spotCount = placeCacheReader.getSpotCount(placeId);
        double placeRate = placeRateCalculator.calculatePlaceRate(placeId);
        return accessor.isMember()
                ? new Detail(place, spotCount, bookmarkReader.isBookmarked(accessor.getMemberId(), placeId), placeRate)
                : new Detail(place, spotCount, placeRate);
    }


    @Override
    @Transactional(readOnly = true)
    public MapStaticImageInfo findPlaceStaticImage(final Long memberId, final String placeName, final String address) {
        var placeOptional = placeReader.findPlaceByNameAndAddress(placeName, address);

        boolean exist = checkExistPlace(placeOptional, memberId);
        return new MapStaticImageInfo(placeOptional, exist);
    }

    private boolean checkExistPlace(Optional<Place> placeOptional, Long memberId) {
        if (placeOptional.isPresent()) {
            var placeId = placeOptional.get().getId();
            return spotReader.isExistPlace(placeId, memberId);
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Place> getNearPlace(final NearPlaceRequest command) {
        return placeReader.getNearPlace(command);
    }

    @Override
    public List<Keyword> getPlaceKeywords(String keywords) {
        return placeReader.getPlaceKeywords(keywords).stream()
                .map(Keyword::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPopularPlaceByDemographics(final Long memberId) {
        var member = memberReader.getMember(memberId);
        Ages ages = member.getAges();
        Gender gender = member.getGender();

        return placeReader.getPopularPlaceByDemographics(ages, gender).stream()
                .map(place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    double placeRate = placeRateCalculator.calculatePlaceRate(place.getId());
                    boolean isBookmarked = bookmarkReader.isBookmarked(memberId, place.getId());
                    return new Main(place, spotCount, isBookmarked, placeRate);

                })
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPopularPlaceByDemographicsMore(final Long memberId) {
        var member = memberReader.getMember(memberId);
        Ages ages = member.getAges();
        Gender gender = member.getGender();

        return placeReader.getPopularPlaceByDemographicsMore(ages, gender).stream()
                .map(place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    double placeRate = placeRateCalculator.calculatePlaceRate(place.getId());
                    boolean isBookmarked = bookmarkReader.isBookmarked(memberId, place.getId());
                    return new Main(place, spotCount, isBookmarked, placeRate);

                })
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public Slice<Main> searchPlace(String keyword, Pageable pageable, Accessor accessor) {
       return placeReader.getPlacesByKeyword(keyword, pageable).map(
                place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    double placeRate = placeRateCalculator.calculatePlaceRate(place.getId());
                    if (accessor.isMember()) {
                        boolean isBookmarked = bookmarkReader.isBookmarked(accessor.getMemberId(), place.getId());
                        return new Main(place, spotCount, isBookmarked, placeRate);
                    }
                    return new Main(place, spotCount, placeRate);
                }
        );
    }

}
