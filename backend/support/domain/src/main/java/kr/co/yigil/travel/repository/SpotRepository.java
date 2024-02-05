package kr.co.yigil.travel.repository;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

    Optional<Spot> findByIdAndMemberId(Long spotId, Long memberId);

    @Query("SELECT s FROM Spot s WHERE s.place.id = :placeId AND s.isInCourse = false")
    List<Spot> findAllByPlaceIdAndIsInCourseFalse(@Param("placeId") Long placeId);

    List<Spot> findAllByMember(Member member);
}