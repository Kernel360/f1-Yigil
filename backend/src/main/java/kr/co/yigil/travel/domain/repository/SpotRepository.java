package kr.co.yigil.travel.domain.repository;

import kr.co.yigil.travel.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Travel, Long> {

}
