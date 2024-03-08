package kr.co.yigil.comment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentReader {

    public int getCommentCount(Long travelId);

    Page<Comment> getParentComments(Long travelId, PageRequest pageRequest);

    Page<Comment> getChildrenComments(Long travelId, PageRequest pageRequest);

    int getChildrenCount(Long parentId);

    Comment getComment(Long commentId);
}
