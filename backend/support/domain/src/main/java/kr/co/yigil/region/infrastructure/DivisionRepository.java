package kr.co.yigil.region.infrastructure;

import java.util.Optional;
import kr.co.yigil.region.domain.Division;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DivisionRepository extends JpaRepository<Division, Integer> {
    @Query(value = "SELECT * FROM Division d WHERE ST_Contains(d.geom, CAST(:location AS geometry))", nativeQuery = true)
    Optional<Division> findContainingDivision(@Param("location") Point location);
}