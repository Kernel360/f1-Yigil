package kr.co.yigil.travel.infrastructure;

import java.util.List;
import java.util.Objects;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.TravelReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TravelReaderImpl implements TravelReader {
    private final TravelRepository travelRepository;

    @Override
    public Travel getTravel(Long travelId) {
        return travelRepository.findById(travelId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_TRAVEL_ID));
    }

    @Override
    public void setTravelsVisibility(Long memberId, List<Long> travelIds,
        boolean isPrivate) {
        travelRepository.findAllById(travelIds)
            .forEach(travel -> {
                if (!Objects.equals(travel.getMember().getId(), memberId)) {
                    throw new AuthException(ExceptionCode.INVALID_AUTHORITY);
                }
                if (travel.isPrivate() == isPrivate) {
                    throw new BadRequestException(ExceptionCode.INVALID_VISIBILITY_REQUEST);
                }
                if (travel.isPrivate()) {
                    travel.changeOnPublic();
                } else {
                    travel.changeOnPrivate();
                }
            });
    }
}
