package kr.co.yigil.travel.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yigil.travel.domain.Travel;

public interface TravelRepository extends JpaRepository<Travel, Long> {
}
