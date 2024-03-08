package kr.co.yigil.place.infrastructure;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.place.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findTop5ByOrderByIdAsc();
    Optional<Place> findByNameAndAddress(String name, String address);
    List<Place> findTop5ByRegionIdOrderByIdDesc(Long regionId);
    List<Place> findTop20ByRegionIdOrderByIdDesc(Long regionId);
    Slice<Place> findByRegionIsNull(Pageable pageable);
    @Query(value = "SELECT p.* FROM Place p WHERE ST_Within(p.location, ST_MakeEnvelope(:minX, :minY, :maxX, :maxY, 4326))", nativeQuery = true)
    Page<Place> findWithinCoordinates(double minX, double minY, double maxX, double maxY, Pageable pageable);
}
