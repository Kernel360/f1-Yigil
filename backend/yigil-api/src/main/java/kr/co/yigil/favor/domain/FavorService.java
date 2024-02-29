package kr.co.yigil.favor.domain;

public interface FavorService {

    Long addFavor(Long userId, Long travelId);

    void deleteFavor(Long memberId, Long travelId);
}
