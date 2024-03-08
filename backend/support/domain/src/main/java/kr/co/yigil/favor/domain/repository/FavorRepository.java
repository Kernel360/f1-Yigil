package kr.co.yigil.favor.domain.repository;

import java.util.Optional;
import kr.co.yigil.favor.domain.Favor;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavorRepository extends JpaRepository<Favor, Long> {
    int countByTravelId(Long travelId);

    void deleteByMemberAndTravel(Member member, Travel travel);

    boolean existsByMemberIdAndTravelId(Long memberId, Long travelId);

    Optional<Favor> findFavorByMemberAndTravel(Member member, Travel travel);

}
