package kr.co.yigil.place.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.bookmark.domain.BookmarkReader;
import kr.co.yigil.place.domain.PlaceInfo.Detail;
import kr.co.yigil.place.domain.PlaceInfo.Main;
import kr.co.yigil.place.domain.PlaceInfo.MapStaticImageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceReader placeReader;
    private final PlaceCacheReader placeCacheReader;
    private final BookmarkReader bookmarkReader;

    @Override
    @Transactional(readOnly = true)
    public List<Main> getPopularPlace(Accessor accessor) {
        return placeReader.getPopularPlace().stream()
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
    public List<Main> getPlaceInRegion(Long regionId, Accessor accessor) {
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
    public Detail retrievePlace(Long placeId, Accessor accessor) {
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
}
