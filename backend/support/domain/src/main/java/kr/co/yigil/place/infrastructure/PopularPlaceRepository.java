package kr.co.yigil.place.infrastructure;

import java.util.List;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PopularPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularPlaceRepository extends JpaRepository<PopularPlace, Long> {

    List<PopularPlace> findTop5ByOrderByReferenceCountDesc();

    List<PopularPlace> findTop20ByOrderByReferenceCountDesc();
}
