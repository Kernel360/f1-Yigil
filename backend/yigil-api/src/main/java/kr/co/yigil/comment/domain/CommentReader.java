package kr.co.yigil.comment.domain;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentReader {
    Optional<Comment> findComment(Long parentId);

    Comment getCommentWithMemberId(Long commentId, Long memberId);

    Slice<Comment> getCommentsByTravelId(Long travelId, Pageable pageable);

    Slice<Comment> getParentCommentsByTravelId(Long travelId, Pageable pageable);

    Slice<Comment> getChildCommentsByParentId(Long parentId, Pageable pageable);

    int getCommentCount(Long travelId);

    Long getTravelIdByCommentId(Long commentId);

    int getChildrenCommentCount(Long travelId);
}
