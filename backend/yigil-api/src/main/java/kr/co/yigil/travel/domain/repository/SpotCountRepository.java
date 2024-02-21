package kr.co.yigil.travel.domain.repository;

import java.util.Optional;
import kr.co.yigil.travel.domain.spot.SpotCount;
import org.springframework.data.repository.CrudRepository;

public interface SpotCountRepository extends CrudRepository<SpotCount, Long> {

    Optional<SpotCount> findByPlaceId(Long placeId);
//    Integer countByPlaceId(Long placeId);
}
