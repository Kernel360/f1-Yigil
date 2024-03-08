package kr.co.yigil.travel.domain.spot;

import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;

public interface SpotSeriesFactory {

    Spot modify(ModifySpotRequest command, Spot spot);

    AttachFiles initAttachFiles(RegisterSpotRequest command);
}
