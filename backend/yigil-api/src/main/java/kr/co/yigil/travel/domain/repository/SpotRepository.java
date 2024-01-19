package kr.co.yigil.travel.domain.repository;

import kr.co.yigil.travel.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

}