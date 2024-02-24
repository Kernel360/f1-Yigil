package kr.co.yigil.travel.domain;

import java.util.List;
import kr.co.yigil.member.domain.MemberInfo.TravelsVisibilityResponse;

public interface TravelReader {
    Travel getTravel(Long travelId);

    TravelsVisibilityResponse setTravelsVisibility(Long memberId, List<Long> courseIds,
        boolean isPrivate);
}
