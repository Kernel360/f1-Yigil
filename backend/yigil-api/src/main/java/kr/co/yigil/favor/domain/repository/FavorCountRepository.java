package kr.co.yigil.favor.domain.repository;

import java.util.Optional;
import kr.co.yigil.favor.domain.FavorCount;
import org.springframework.data.repository.CrudRepository;

public interface FavorCountRepository extends CrudRepository<FavorCount, Long> {

    Optional<FavorCount> findByTravelId(Long travelid);
}
