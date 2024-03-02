package kr.co.yigil.comment.domain;


import kr.co.yigil.comment.domain.CommentCommand.CommentCreateRequest;
import kr.co.yigil.comment.domain.CommentCommand.CommentUpdateRequest;
import kr.co.yigil.comment.domain.CommentInfo.CommentsResponse;
import kr.co.yigil.comment.domain.CommentInfo.CommentNotiInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    CommentNotiInfo createComment(Long memberId, Long travelId,
        CommentCreateRequest commentCreateRequest);

    void deleteComment(Long memberId, Long commentId);

    CommentsResponse getParentComments(Long travelId, Pageable pageable);

    CommentsResponse getChildComments(Long parentId, Pageable pageable);

    CommentNotiInfo updateComment(Long commentId, Long memberId, CommentUpdateRequest command);
}

