package kr.co.yigil.place.infrastructure;

import java.util.List;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.place.domain.DemographicPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemographicPlaceRepository extends JpaRepository<DemographicPlace, Long> {
    List<DemographicPlace> findTop5ByAgesAndGenderOrderByReferenceCountDesc(Ages ages, Gender gender);

    List<DemographicPlace> findTop20ByAgesAndGenderOrderByReferenceCountDesc(Ages ages, Gender gender);
}
