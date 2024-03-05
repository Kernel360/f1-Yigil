package kr.co.yigil.place.infrastructure;

import kr.co.yigil.place.domain.PopularPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularPlaceRepository extends JpaRepository<PopularPlace, Long> {

}
