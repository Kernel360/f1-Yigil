package kr.co.yigil.favor.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.TravelReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(favorReader.existsByMemberIdAndTravelId(memberId, travelId))
            throw new IllegalArgumentException("이미 좋아요를 누른 게시글입니다.");

        Member member = memberReader.getMember(memberId);
        Travel travel = travelReader.getTravel(travelId);
        favorStore.save(new Favor(member, travel));
        favorCountCacheStore.incrementFavorCount(travelId);
        return travel.getMember().getId();
    }

    @Override
    @Transactional
    public void deleteFavor(Long memberId, Long travelId) {
        Long favorId = favorReader.getFavorIdByMemberIdAndTravelId(memberId, travelId);
        favorStore.deleteFavorById(favorId);
        favorCountCacheStore.decrementFavorCount(travelId);
    }
}
