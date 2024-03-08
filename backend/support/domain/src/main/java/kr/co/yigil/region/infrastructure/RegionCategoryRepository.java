package kr.co.yigil.region.infrastructure;

import kr.co.yigil.region.domain.RegionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionCategoryRepository extends JpaRepository<RegionCategory, Long> {

}
