package kr.co.yigil.place.repository;

import java.util.Optional;
import kr.co.yigil.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByName(String name);
    Optional<Place> findByNameAndAddress(String name, String address);
}
