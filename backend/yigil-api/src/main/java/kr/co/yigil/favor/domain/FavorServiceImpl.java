package kr.co.yigil.favor.domain;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.TravelReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FavorServiceImpl implements FavorService{

    private final FavorReader favorReader;
    private final MemberReader memberReader;
    private final FavorStore favorStore;
    private final TravelReader travelReader;
    private final FavorCountCacheStore favorCountCacheStore;

    @Override
    @Transactional
    public Long addFavor(Long memberId, Long travelId) {
        Member member = memberReader.getMember(memberId);
        Travel travel = travelReader.getTravel(travelId);

        validateRequest(memberId, travel);

        favorStore.save(new Favor(member, travel));
        favorCountCacheStore.incrementFavorCount(travelId);
        return travel.getMember().getId();
    }

    private void validateRequest(Long giverId, Travel travel) {
        if(favorReader.existsByMemberIdAndTravelId(giverId, travel.getId()))
            throw new BadRequestException(ExceptionCode.ALREADY_FAVOR);

        if(Objects.equals(travel.getWriterId(), giverId))
            throw new BadRequestException(ExceptionCode.CANNOT_FAVOR_OWN_TRAVEL);
    }

    @Override
    @Transactional
    public void deleteFavor(Long memberId, Long travelId) {
        Member member = memberReader.getMember(memberId);
        Travel travel = travelReader.getTravel(travelId);
        Long favorId = favorReader.getFavorIdByMemberAndTravel(member, travel);
        favorStore.deleteFavorById(favorId);
        favorCountCacheStore.decrementFavorCount(travelId);
    }
}
