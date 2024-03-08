package kr.co.yigil.travel.domain.spot;

import kr.co.yigil.travel.domain.Spot;

public interface SpotStore {
    Spot store(Spot initSpot);

    void remove(Spot spot);
}
