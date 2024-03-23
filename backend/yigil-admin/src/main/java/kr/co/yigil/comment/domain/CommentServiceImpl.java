package kr.co.yigil.comment.domain;

import kr.co.yigil.comment.domain.CommentInfo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentReader commentReader;
    private final CommentStore commentStore;
    @Override
    @Transactional(readOnly = true)
    public ParentPageComments getParentComments(Long travelId, PageRequest pageRequest) {
        Page<Comment> comments = commentReader.getParentComments(travelId, pageRequest);
        List<ParentListInfo> parentListInfos = comments.getContent().stream().map(
            comment -> {
                int childrenCount = commentReader.getChildrenCount(comment.getId());
                return new ParentListInfo(comment, childrenCount);
            }
        ).toList();

        return new ParentPageComments(parentListInfos, comments.getPageable(), comments.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public ChildrenPageComments getChildrenComments(Long travelId, PageRequest pageRequest) {
        Page<Comment> comments = commentReader.getChildrenComments(travelId, pageRequest);

        List<ChildrenListInfo> childrenListInfos = comments.getContent().stream()
            .map(ChildrenListInfo::new)
            .toList();

        return new ChildrenPageComments(childrenListInfos, comments.getPageable(), comments.getTotalElements());
    }

    @Override
    @Transactional
    public Long deleteComment(Long commentId) {
        Comment comment = commentReader.getComment(commentId);
        commentStore.deleteComment(comment);
        return comment.getMember().getId();
    }

    @Override
    public CommentList getComments(Long travelId, PageRequest pageRequest) {
        Page<Comment> comments = commentReader.getParentComments(travelId, pageRequest);

        Pageable pageable = comments.getPageable();
        long total = comments.getTotalElements();

        List<CommentListUnit> commentListUnits = comments.getContent().stream().map(
            comment -> {
                List<ReplyListUnit> replyListUnits = commentReader.getChildrenComments(comment.getId()).stream()
                    .map(ReplyListUnit::new)
                    .toList();
                return new CommentListUnit(comment, replyListUnits);
            }
        ).toList();

        return new CommentList(commentListUnits, pageable, total);

    }
}
