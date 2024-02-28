package kr.co.yigil.region.infrastructure;

import java.util.Optional;
import kr.co.yigil.region.domain.Division;
import kr.co.yigil.region.domain.DongDivision;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DongDivisionRepository extends JpaRepository<DongDivision, Integer> {
    @Query(value = "SELECT * FROM DongDivision d WHERE ST_Contains(d.geom, CAST(:location AS geography))", nativeQuery = true)
    Optional<DongDivision> findContainingDivision(@Param("location") Point location);
}
