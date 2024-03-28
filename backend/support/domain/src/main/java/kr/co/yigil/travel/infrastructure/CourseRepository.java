package kr.co.yigil.travel.infrastructure;

import kr.co.yigil.member.Member;
import kr.co.yigil.travel.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByIdAndMemberId(Long courseId, Long memberId);

    @Query("SELECT c FROM Course c JOIN c.spots s WHERE s.place.id = :placeId AND c.isPrivate = false")
    Slice<Course> findBySpotPlaceId(@Param("placeId") Long placeId, Pageable pageable);

    Slice<Course> findBySpots_PlaceIdAndIsPrivateFalse(Long placeId, Pageable pageable);

    Page<Course> findAllByMemberId(Long memberId, Pageable pageable);

    Page<Course> findAllByMemberIdAndIsPrivate(Long memberId, boolean isPrivate, Pageable pageable);

    Slice<Course> findAllByMember(Member member);

    @Query("SELECT c FROM Course c JOIN c.spots s WHERE s.place.name LIKE %:keyword% AND c.isPrivate = false")
    Slice<Course> findByPlaceNameContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT c FROM Course c inner JOIN Favor f ON c.id = f.travel.id WHERE c.isDeleted = false AND f.member.id = :memberId")
    Slice<Course> findAllMembersFavoriteCourses(Long memberId, Pageable pageRequest);
}