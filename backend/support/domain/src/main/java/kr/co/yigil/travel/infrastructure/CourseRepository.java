package kr.co.yigil.travel.infrastructure;

import java.util.Optional;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.domain.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByIdAndMemberId(Long courseId, Long memberId);

    @Query("SELECT c FROM Course c JOIN c.spots s WHERE s.place.id = :placeId AND c.isPrivate = false")
    Slice<Course> findBySpotPlaceId(@Param("placeId") Long placeId, Pageable pageable);

    Slice<Course> findBySpots_PlaceIdAndIsPrivateFalse(Long placeId, Pageable pageable);

    Slice<Course> findAllByMember(Member member);
}