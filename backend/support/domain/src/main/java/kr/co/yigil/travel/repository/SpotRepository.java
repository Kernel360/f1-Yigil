package kr.co.yigil.travel.repository;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.Spot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

    Optional<Spot> findByIdAndMemberId(Long spotId, Long memberId);

    Slice<Spot> findAllByPlaceIdAndIsInCourseFalse(@Param("placeId") Long placeId, Pageable pageable);

    List<Spot> findAllByMember(Member member);
}
