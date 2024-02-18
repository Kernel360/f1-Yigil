package kr.co.yigil.place.repository;

import java.util.Optional;
import kr.co.yigil.place.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByName(String name);
    Optional<Place> findByNameAndAddress(String name, String address);
    @Query("SELECT p FROM Place p")
    Slice<Place>  findAllPlaces(Pageable pageable);
}
