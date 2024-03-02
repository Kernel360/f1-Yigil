package kr.co.yigil.place.infrastructure;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.place.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findTop5ByOrderByIdAsc();
    Optional<Place> findByNameAndAddress(String name, String address);
    List<Place> findTop5ByRegionIdOrderByIdDesc(Long regionId);
    Slice<Place> findByRegionIsNull(Pageable pageable);
}
