package kr.co.yigil.place.domain;

public interface PlaceCacheReader {
    int getSpotCount(Long placeId);

    double getSpotTotalRate(Long placeId);
}
