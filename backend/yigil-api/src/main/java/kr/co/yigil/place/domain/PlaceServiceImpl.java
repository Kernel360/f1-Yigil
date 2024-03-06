package kr.co.yigil.place.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.bookmark.domain.BookmarkReader;
import kr.co.yigil.place.domain.PlaceCommand.NearPlaceRequest;
import kr.co.yigil.place.domain.PlaceInfo.Detail;
import kr.co.yigil.place.domain.PlaceInfo.Main;
import kr.co.yigil.place.domain.PlaceInfo.MapStaticImageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceReader placeReader;
    private final PopularPlaceReader popularPlaceReader;
    private final PlaceCacheReader placeCacheReader;
    private final BookmarkReader bookmarkReader;

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPopularPlace(final Accessor accessor) {
        return popularPlaceReader.getPopularPlace().stream()
                .map(place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    if (accessor.isMember()) {
                        boolean isBookmarked = bookmarkReader.isBookmarked(accessor.getMemberId(), place.getId());
                        return new Main(place, spotCount, isBookmarked);
                    }
                    return new Main(place, spotCount);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPopularPlaceMore(final Accessor accessor) {
        return popularPlaceReader.getPopularPlaceMore().stream()
                .map(place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    if (accessor.isMember()) {
                        boolean isBookmarked = bookmarkReader.isBookmarked(accessor.getMemberId(), place.getId());
                        return new Main(place, spotCount, isBookmarked);
                    }
                    return new Main(place, spotCount);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPlaceInRegion(final Long regionId, final Accessor accessor) {
        return placeReader.getPlaceInRegion(regionId).stream()
                .map(place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    if (accessor.isMember()) {
                        boolean isBookmarked = bookmarkReader.isBookmarked(accessor.getMemberId(), place.getId());
                        return new Main(place, spotCount, isBookmarked);
                    }
                    return new Main(place, spotCount);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPlaceInRegionMore(Long regionId, Accessor accessor) {
        return placeReader.getPlaceInRegionMore(regionId).stream()
                .map(place -> {
                    int spotCount = placeCacheReader.getSpotCount(place.getId());
                    if (accessor.isMember()) {
                        boolean isBookmarked = bookmarkReader.isBookmarked(accessor.getMemberId(), place.getId());
                        return new Main(place, spotCount, isBookmarked);
                    }
                    return new Main(place, spotCount);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Detail retrievePlace(final Long placeId, final Accessor accessor) {
        var place = placeReader.getPlace(placeId);
        int spotCount = placeCacheReader.getSpotCount(placeId);
        return accessor.isMember()
                ? new Detail(place, spotCount, bookmarkReader.isBookmarked(accessor.getMemberId(), placeId))
                : new Detail(place, spotCount);
    }

    @Override
    @Transactional(readOnly = true)
    public MapStaticImageInfo findPlaceStaticImage(final String placeName, final String address) {
        var placeOptional = placeReader.findPlaceByNameAndAddress(placeName, address);
        return new MapStaticImageInfo(placeOptional);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Place> getNearPlace(final NearPlaceRequest command) {
        return placeReader.getNearPlace(command);
    }
}
