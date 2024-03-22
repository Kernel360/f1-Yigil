package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.travel.TravelType;

import java.util.List;

public interface DailyFavorCountRepositoryCustom {

    public List<DailyFavorCount> findTopByTravelTypeOrderByCountDesc(TravelType travelType);
}
