package kr.co.yigil.favor.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.travel.domain.Travel;

public interface FavorReader {

    boolean existsByMemberIdAndTravelId(Long memberId, Long travelId);

    Long getFavorIdByMemberAndTravel(Member member, Travel travel);

}
