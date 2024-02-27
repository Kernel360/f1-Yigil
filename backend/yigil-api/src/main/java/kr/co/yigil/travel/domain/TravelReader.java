package kr.co.yigil.travel.domain;

import java.util.List;

public interface TravelReader {
    Travel getTravel(Long travelId);
    public List<Travel> getTravels(List<Long> travelIds);
}
