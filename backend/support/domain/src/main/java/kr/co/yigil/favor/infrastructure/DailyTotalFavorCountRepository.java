package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.DailyTotalFavorCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyTotalFavorCountRepository extends JpaRepository<DailyTotalFavorCount, Long>{
}
