package kr.co.yigil.post.domain.repository;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    void deleteByMemberIdAndTravelId(Long memberId, Long travelId);

    @Query(value = "SELECT p.* FROM Post p WHERE p.member_id = :memberId AND p.travel_id = :travelId AND p.is_deleted = :isDeleted", nativeQuery = true)
    Optional<Post> findByMemberIdAndTravelIdAndIsDeleted(Long memberId, Long travelId, boolean isDeleted);

    List<Post> findAllByMember(Member member);

    boolean existsByMemberIdAndId(Long memberId, Long postId);
}
