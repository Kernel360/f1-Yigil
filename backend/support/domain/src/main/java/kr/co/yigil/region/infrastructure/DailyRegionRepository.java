package kr.co.yigil.region.infrastructure;

import java.time.LocalDate;
import java.util.List;
import kr.co.yigil.region.domain.DailyRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DailyRegionRepository extends JpaRepository<DailyRegion, Long> {
    @Query("SELECT new kr.co.yigil.region.domain.DailyRegion(dr.region, SUM(dr.referenceCount)) FROM DailyRegion dr WHERE dr.date >= :startDate AND dr.date <= :endDate GROUP BY dr.region")
    List<DailyRegion> findBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
