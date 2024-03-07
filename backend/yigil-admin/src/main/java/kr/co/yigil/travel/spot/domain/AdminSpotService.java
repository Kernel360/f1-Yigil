package kr.co.yigil.travel.spot.domain;

import kr.co.yigil.travel.spot.domain.AdminSpotInfoDto.AdminSpotDetailInfo;
import kr.co.yigil.travel.spot.domain.AdminSpotInfoDto.AdminSpotList;
import org.springframework.data.domain.Pageable;

public interface AdminSpotService {


    AdminSpotDetailInfo getSpot(Long spotId);

    AdminSpotList getSpots(Pageable pageable);

    Long deleteSpot(Long spotId);
}
