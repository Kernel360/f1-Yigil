package kr.co.yigil.travel.spot.domain;

import kr.co.yigil.travel.spot.domain.SpotInfoDto.AdminSpotDetailInfo;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.AdminSpotList;
import org.springframework.data.domain.Pageable;

public interface SpotService {


    AdminSpotDetailInfo getSpot(Long spotId);

    AdminSpotList getSpots(Pageable pageable);

    Long deleteSpot(Long spotId);
}
