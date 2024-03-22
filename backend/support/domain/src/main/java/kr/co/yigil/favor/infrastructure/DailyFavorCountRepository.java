package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.travel.TravelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DailyFavorCountRepository extends JpaRepository<DailyFavorCount, Long>, DailyFavorCountRepositoryCustom {
    Page<DailyFavorCount> findTop5ByOrderByCountDesc(Pageable pageable);

    Page<DailyFavorCount> findAllByCreatedAtOrderByCountDesc(LocalDate createdAt, Pageable pageable);

    Page<DailyFavorCount> findAllByCreatedAtBetweenAndTravelTypeOrderByCountDesc(LocalDate startDate, LocalDate endDate, TravelType travelType, Pageable pageable);
}
