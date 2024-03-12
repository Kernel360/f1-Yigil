package kr.co.yigil.travel.spot.domain;

import kr.co.yigil.travel.domain.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpotReader {

    Page<Spot> getSpots(Pageable pageable);

    Spot getSpot(Long spotId);

}
