package kr.co.yigil.region.infrastructure;

import kr.co.yigil.region.domain.DailyRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRegionRepository extends JpaRepository<DailyRegion, Long> {

}
