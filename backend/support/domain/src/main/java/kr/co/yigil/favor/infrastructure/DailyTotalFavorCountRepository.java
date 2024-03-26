package kr.co.yigil.favor.infrastructure;

import java.time.LocalDate;
import java.util.List;
import kr.co.yigil.favor.domain.DailyTotalFavorCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DailyTotalFavorCountRepository extends JpaRepository<DailyTotalFavorCount, Long>{
    @Query("SELECT new kr.co.yigil.favor.domain.DailyTotalFavorCount(dtf.count, dtf.date) FROM DailyTotalFavorCount dtf WHERE dtf.date >= :startDate AND dtf.date <= :endDate")
    List<DailyTotalFavorCount> findBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
