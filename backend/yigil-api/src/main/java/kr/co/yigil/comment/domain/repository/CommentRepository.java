package kr.co.yigil.comment.domain.repository;

import java.util.List;
import kr.co.yigil.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long>  {

    @Query("SELECT c FROM Comment c " +
            "LEFT JOIN FETCH c.parent " +
            "WHERE c.post.id = :postId " +
            "ORDER BY c.parent.id ASC NULLS FIRST, c.createdAt ASC")
    List<Comment> findCommentListByPostId(@Param("postId") Long postId);

    List<Comment> findByPostIdOrderById(Long postId);

    boolean existsByMemberIdAndId(Long memberId, Long commentId);

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.parent IS NULL "
        + "ORDER BY c.createdAt ASC "
    )
    List<Comment> findTopLevelCommentsByPostId(@Param("postId") Long postId);

    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.isDeleted = false AND c.parent.id = :parentId "
        + "ORDER BY c.createdAt ASC "
    )
    List<Comment> findRepliesByPostIdAndParentId(@Param("postId") Long postId, @Param("parentId") Long parentId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId AND c.isDeleted = false")
    int countNonDeletedCommentsByPostId(@Param("postId") Long postId);

}
