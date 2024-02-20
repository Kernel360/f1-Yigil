package kr.co.yigil.travel.domain;

import java.util.Objects;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {

    private final TravelReader travelReader;

    @Override
    @Transactional
    public void changeOnPublic(Long travelId, Long memberId) {
        Travel travel = travelReader.getTravel(travelId);
        validateTravelOwner(travel, memberId);
        travel.changeOnPublic();
    }

    @Override
    public void changeOnPrivate(Long travelId, Long memberId) {
        Travel travel = travelReader.getTravel(travelId);
        validateTravelOwner(travel, memberId);
        travel.changeOnPrivate();
    }

    private void validateTravelOwner(Travel travel, Long memberId) {
        if(!Objects.equals(travel.getMember().getId(), memberId)) throw new AuthException(ExceptionCode.INVALID_AUTHORITY);
    }
}
