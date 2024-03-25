package kr.co.yigil.travel.infrastructure;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.travel.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TravelRepository extends JpaRepository<Travel, Long> {

    Optional<Travel> findByIdAndMemberId(Long travelId, Long memberId);

    @Query("SELECT COUNT(t) FROM Travel t WHERE CAST(t.createdAt AS date) = CURRENT_DATE AND t.isDeleted = false")
    long countByCreatedAtToday();
    List<Travel> findTop5ByOrderByCreatedAtDesc();
}
