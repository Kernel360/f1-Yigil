package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.FavorReader;
import kr.co.yigil.favor.domain.repository.FavorRepository;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavorReaderImpl implements FavorReader {
    private final FavorRepository favorRepository;

    @Override
    public boolean existsByMemberIdAndTravelId(Long memberId, Long travelId) {
        return favorRepository.existsByMemberIdAndTravelId(memberId, travelId);
    }

    @Override
    public Long getFavorIdByMemberIdAndTravelId(Long memberId, Long travelId) {
        return favorRepository.getFavorIdByMemberIdAndTravelId(memberId, travelId)
            .orElseThrow(() -> new BadRequestException(ExceptionCode.FAVOR_NOT_FOUND));
    }
}
