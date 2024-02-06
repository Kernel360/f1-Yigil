package kr.co.yigil.place.repository;

import java.util.Optional;
import kr.co.yigil.place.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByName(String name);
    Optional<Place> findByNameAndAddress(String name, String address);

    @Query("SELECT p FROM Place p WHERE p.name LIKE %:keyword% OR p.address LIKE %:keyword%")
    Slice<Place> findPlacesByNameOrAddressContainingKeyword(Pageable pageable, @Param("keyword") String keyword);}
