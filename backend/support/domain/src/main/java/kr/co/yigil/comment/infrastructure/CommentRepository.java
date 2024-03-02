package kr.co.yigil.comment.infrastructure;

import java.util.Optional;
import kr.co.yigil.comment.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c " +
        "LEFT JOIN FETCH c.parent " +
        "WHERE c.travel.id = :travelId " +
        "ORDER BY c.parent.id ASC NULLS FIRST, c.createdAt ASC")
    Slice<Comment> findCommentListByTravelId(@Param("travelId") Long travelId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.travel.id = :travelId AND c.parent IS NULL "
        + "ORDER BY c.createdAt ASC "
    )
    Slice<Comment> findParentCommentsByTravelId(@Param("travelId") Long travelId,
        Pageable pageable);

    Slice<Comment> findAllByTravelIdAndParentIsNull(Long travelId, Pageable pageable);


    @Query("SELECT c FROM Comment c WHERE c.isDeleted = false AND c.parent.id = :parentId "
        + "ORDER BY c.createdAt ASC "
    )
    Slice<Comment> findChildCommentsByParentId(@Param("parentId") Long parentId, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.travel.id = :travelId AND c.isDeleted = false")
    int countNonDeletedCommentsByTravelId(@Param("travelId") Long travelId);

    int countAllByTravelIdAndIsDeletedFalse(Long travelId);

    Optional<Comment> findByIdAndMemberId(Long commentId, Long memberId);

    @Query("SELECT c.travel.id FROM Comment c WHERE c.id = :commentId")
    Optional<Long> findTravelIdByCommentId(@Param("commentId") Long commentId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.parent.id = :parentId")
    int countByParentId(@Param("parentId") Long parentId);

}
