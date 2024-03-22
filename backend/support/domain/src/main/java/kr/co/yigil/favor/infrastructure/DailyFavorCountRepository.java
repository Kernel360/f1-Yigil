package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.DailyFavorCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyFavorCountRepository extends JpaRepository<DailyFavorCount, Long> {
    List<DailyFavorCount> findTop5ByOrderByCountDesc();
}
