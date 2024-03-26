package kr.co.yigil.stats.domain;

import java.util.List;
import kr.co.yigil.travel.domain.Travel;

public interface TravelReader {

    long getTodayTravelCnt();

    List<Travel> getRecentTravel();
}
