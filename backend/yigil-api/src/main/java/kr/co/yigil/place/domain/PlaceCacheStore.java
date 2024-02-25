package kr.co.yigil.place.domain;

public interface PlaceCacheStore {
    int incrementSpotCountInPlace(Long placeId);
    int decrementSpotCountInPlace(Long placeId);
}
