package kr.co.yigil.travel.domain;

import java.util.List;
import java.util.Objects;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.global.exception.BadRequestException;
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

    @Override
    @Transactional
    public void setTravelsVisibility(Long memberId,
        TravelCommand.VisibilityChangeRequest travelCommand) {
        List<Long> travelIds = travelCommand.getTravelIds();

        travelReader.getTravels(travelIds)
            .forEach(travel -> {
                validateTravelOwner(travel, memberId);
                if (travel.isPrivate() == travelCommand.getIsPrivate()) {
                    throw new BadRequestException(ExceptionCode.INVALID_VISIBILITY_REQUEST);
                }
                if (travel.isPrivate()) travel.changeOnPublic();
                else travel.changeOnPrivate();
            });
    }


    private void validateTravelOwner(Travel travel, Long memberId) {
        if(!Objects.equals(travel.getMember().getId(), memberId)) throw new AuthException(ExceptionCode.INVALID_AUTHORITY);
    }
}
