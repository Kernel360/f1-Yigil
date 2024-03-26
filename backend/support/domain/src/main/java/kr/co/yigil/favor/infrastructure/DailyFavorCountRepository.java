package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.DailyFavorCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DailyFavorCountRepository extends JpaRepository<DailyFavorCount, Long>, DailyFavorCountRepositoryCustom {

    @Query("SELECT d.createdAt, SUM(d.count) FROM DailyFavorCount d GROUP BY d.createdAt")
    Page<Object[]> findTotalLikesPerDay(Pageable pageable);
}
