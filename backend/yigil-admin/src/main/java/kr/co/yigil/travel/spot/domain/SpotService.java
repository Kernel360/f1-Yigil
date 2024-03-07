package kr.co.yigil.travel.spot.domain;

import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotDetailInfo;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotPageInfo;
import org.springframework.data.domain.Pageable;

public interface SpotService {


    SpotDetailInfo getSpot(Long spotId);

    SpotPageInfo getSpots(Pageable pageable);

    Long deleteSpot(Long spotId);
}
