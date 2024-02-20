package kr.co.yigil.place.domain.repository;

import java.util.Optional;
import kr.co.yigil.place.domain.PlaceRate;
import org.springframework.data.repository.CrudRepository;

public interface PlaceRateRepository extends CrudRepository<PlaceRate, Long> {

    Optional<PlaceRate> findByPlaceId(Long placeId);
}
