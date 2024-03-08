package kr.co.yigil.travel.infrastructure;

import java.time.LocalDateTime;
import java.util.Optional;
import kr.co.yigil.travel.domain.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

    Slice<Spot> findAllByPlaceIdAndIsInCourseIsFalseAndIsPrivateIsFalse(Long placeId, Pageable pageable);

    Optional<Spot> findTopByPlaceIdAndMemberId(Long placeId, Long memberId);

    @Query("SELECT count(s) FROM Spot s WHERE s.place.id = :placeId AND s.isDeleted = false")
    int countByPlaceId(@Param("placeId") Long placeId);

    Page<Spot> findAllByMemberIdAndIsInCourseIsFalse(Long memberId, Pageable pageable);
    Page<Spot> findAllByMemberIdAndIsPrivateAndIsInCourseFalse(Long memberId, boolean isPrivate, Pageable pageable);

    @Query("SELECT SUM(s.rate) FROM Spot s WHERE s.place.id = :placeId")
    Optional<Double> findTotalRateByPlaceId(@Param("placeId") Long placeId);

    Page<Spot> findAllByMemberId(Long memberId, Pageable pageable);
    Page<Spot> findAllByMemberIdAndIsPrivate(Long memberId, boolean isPrivate, Pageable pageable);

    @Query("SELECT s.place, COUNT(s) AS referenceCount FROM Spot s WHERE s.createdAt BETWEEN :startDate AND :endDate GROUP BY s.place ORDER BY referenceCount DESC")
    Slice<Object[]> findPlaceReferenceCountBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("SELECT s.place, COUNT(s) AS referenceCount, m.ages, m.gender FROM Spot s INNER JOIN s.member m WHERE s.createdAt BETWEEN :startDate AND :endDate GROUP BY s.place, m.ages, m.gender ORDER BY referenceCount DESC, m.ages asc, m.gender ASC")
    Slice<Object[]> findPlaceReferenceCountGroupByDemographicBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}