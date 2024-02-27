package kr.co.yigil.travel.infrastructure;

import java.util.Optional;
import kr.co.yigil.travel.domain.Spot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

    Slice<Spot> findAllByPlaceIdAndIsInCourseIsFalseAndIsPrivateIsFalse(Long placeId, Pageable pageable);

    Optional<Spot> findByPlaceIdAndMemberId(Long placeId, Long memberId);

    @Query("SELECT count(s) FROM Spot s WHERE s.place.id = :placeId AND s.isDeleted = false")
    int countByPlaceId(@Param("placeId") Long placeId);

}