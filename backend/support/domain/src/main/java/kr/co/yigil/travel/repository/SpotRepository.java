package kr.co.yigil.travel.repository;

import java.util.Optional;
import kr.co.yigil.travel.Spot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

    Optional<Spot> findByIdAndMemberId(Long spotId, Long memberId);

    @Query("SELECT s FROM Spot s WHERE s.place.id = :placeId AND s.isInCourse = false AND s.isPrivate = false")
    Slice<Spot> findAllByPlaceId(@Param("placeId") Long placeId,
            Pageable pageable);

    Optional<Spot> findByPlaceIdAndMemberId(Long placeId, Long memberId);

    @Query("SELECT count(s) FROM Spot s WHERE s.place.id = :placeId AND s.isDeleted = false")
    int countByPlaceId(@Param("placeId") Long placeId);

    @Query("SELECT s FROM Spot s WHERE s.member.id = :memberId AND s.isInCourse = false")
    Slice<Spot> findAllByMemberAndIsInCourseFalse(Long memberId, Pageable pageable);

    @Query("SELECT SUM(s.rate) FROM Spot s WHERE s.place.id = :placeId")
    Optional<Double> findTotalRateByPlaceId(@Param("placeId") Long placeId);
}