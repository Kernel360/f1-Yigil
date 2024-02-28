package kr.co.yigil.favor.domain;

public interface FavorReader {

    boolean existsByMemberIdAndTravelId(Long memberId, Long travelId);

    Long getFavorIdByMemberIdAndTravelId(Long memberId, Long travelId);


}
