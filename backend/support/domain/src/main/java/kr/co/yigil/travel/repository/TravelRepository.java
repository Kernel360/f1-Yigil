package kr.co.yigil.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yigil.travel.Travel;

public interface TravelRepository extends JpaRepository<Travel, Long> {
}