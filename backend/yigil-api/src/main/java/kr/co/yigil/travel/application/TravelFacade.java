package kr.co.yigil.travel.application;

import kr.co.yigil.travel.domain.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelFacade {
    private final TravelService travelService;

    public void changeOnPublicTravel(Long travelId, Long memberId) { travelService.changeOnPublic(travelId, memberId);}

    public void changeOnPrivateTravel(Long travelId, Long memberId) { travelService.changeOnPrivate(travelId, memberId);}
}
