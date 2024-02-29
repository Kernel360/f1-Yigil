package kr.co.yigil.region.infrastructure;

import java.util.Optional;
import kr.co.yigil.region.domain.Division;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DivisionRepository extends JpaRepository<Division, Integer> {
    @Query(value = "SELECT * FROM Division2 d WHERE ST_Contains(d.geom, ST_SetSRID(CAST(:location AS geometry), 5186)) LIMIT 1", nativeQuery = true)
    Optional<Division> findContainingDivision(@Param("location") Point location);
}
