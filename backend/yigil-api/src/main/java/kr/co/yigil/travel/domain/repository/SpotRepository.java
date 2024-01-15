package kr.co.yigil.travel.domain.repository;

import kr.co.yigil.travel.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Travel, Long> {

}