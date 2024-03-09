package kr.co.yigil.comment.domain;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentReader {

    public int getCommentCount(Long travelId);

    Page<Comment> getParentComments(Long travelId, PageRequest pageRequest);

    Page<Comment> getChildrenComments(Long travelId, PageRequest pageRequest);
    List<Comment> getChildrenComments(Long travelId);

    int getChildrenCount(Long parentId);

    Comment getComment(Long commentId);
}
