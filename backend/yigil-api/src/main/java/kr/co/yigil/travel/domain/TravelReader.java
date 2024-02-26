package kr.co.yigil.travel.domain;

import java.util.List;

public interface TravelReader {
    Travel getTravel(Long travelId);

    void setTravelsVisibility(Long memberId, List<Long> courseIds,
        boolean isPrivate);
}
