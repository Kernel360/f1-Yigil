package kr.co.yigil.region.infrastructure;

import kr.co.yigil.region.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{
    boolean isExistRegion(Long regionId);
}
