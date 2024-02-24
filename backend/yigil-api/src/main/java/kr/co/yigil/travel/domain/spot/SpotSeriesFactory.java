package kr.co.yigil.travel.domain.spot;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;

public interface SpotSeriesFactory {

    Spot modify(ModifySpotRequest command, Spot spot);
}
