package kr.co.yigil.comment.domain;

import kr.co.yigil.comment.domain.CommentInfo.ChildrenPageComments;
import kr.co.yigil.comment.domain.CommentInfo.CommentList;
import kr.co.yigil.comment.domain.CommentInfo.ParentPageComments;
import org.springframework.data.domain.PageRequest;

public interface CommentService {

    ParentPageComments getParentComments(Long travelId, PageRequest pageRequest);

    ChildrenPageComments getChildrenComments(Long travelId, PageRequest pageRequest);

    Long deleteComment(Long commentId);

    CommentList getComments(Long travelId, PageRequest pageRequest);
}
