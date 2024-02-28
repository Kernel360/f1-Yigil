package kr.co.yigil.favor.domain;

public interface FavorStore {

    void save(Favor favor);

    void deleteFavorById(Long favorId);
}
