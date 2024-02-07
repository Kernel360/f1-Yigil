package kr.co.yigil.travel.application;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.Travel;
import kr.co.yigil.travel.dto.response.PrivateUpdateResponse;
import kr.co.yigil.travel.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;

    public Travel findTravelById(Long travelId) {
        return travelRepository.findById(travelId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_TRAVEL_ID));
    }

    @Transactional
    public PrivateUpdateResponse togglePrivate(Long travelId, Long memberId) {
        Travel travel = travelRepository.findByIdAndMemberId(travelId, memberId)
                .orElseThrow(
                        () -> new BadRequestException(ExceptionCode.NOT_FOUND_TRAVEL_ID)
                );
        travel.togglePrivate();
        return new PrivateUpdateResponse("리뷰 공개 상태 변경 완료");
    }
}
