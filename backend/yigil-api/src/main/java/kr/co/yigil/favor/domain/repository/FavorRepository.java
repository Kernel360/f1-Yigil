package kr.co.yigil.favor.domain.repository;

import kr.co.yigil.favor.domain.Favor;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavorRepository extends JpaRepository<Favor, Long> {

    public int countByTravelId(Long travelId);

    public void deleteByMemberAndTravel(Member member, Travel travel);
}
