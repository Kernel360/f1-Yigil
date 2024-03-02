package kr.co.yigil.favor.domain;

public interface FavorCountCacheStore {
    int incrementFavorCount(Long favorId);
    int decrementFavorCount(Long favorId);
}
