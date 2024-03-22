package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.travel.TravelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface DailyFavorCountRepository extends JpaRepository<DailyFavorCount, Long>, DailyFavorCountRepositoryCustom {
    @Query("SELECT d FROM DailyFavorCount d WHERE d.createdAt BETWEEN :startDate AND :endDate AND (d.travelType = :travelType OR :travelType = 'ALL') ORDER BY d.count DESC")
    Page<DailyFavorCount> findAllByCreatedAtBetweenAndTravelTypeOrAllOrderByCountDesc(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("travelType") TravelType travelType, Pageable pageable);
}
