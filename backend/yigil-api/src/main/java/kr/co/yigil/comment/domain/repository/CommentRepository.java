package kr.co.yigil.comment.domain.repository;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import kr.co.yigil.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long>  {

    @Query("SELECT c FROM Comment c " +
            "LEFT JOIN FETCH c.parent " +
            "WHERE c.travel.id = :travelId " +
            "ORDER BY c.parent.id ASC NULLS FIRST, c.createdAt ASC")
    List<Comment> findCommentListByTravelId(@Param("travelId") Long travelId);

    boolean existsByMemberIdAndId(Long memberId, Long commentId);

    int countByTravelId(Long id);
}
