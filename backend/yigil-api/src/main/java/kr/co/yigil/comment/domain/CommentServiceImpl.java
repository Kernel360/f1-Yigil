package kr.co.yigil.comment.domain;


import java.util.List;
import kr.co.yigil.comment.domain.CommentCommand.CommentUpdateRequest;
import kr.co.yigil.comment.domain.CommentInfo.CommentNotiInfo;
import kr.co.yigil.comment.domain.CommentInfo.CommentsResponse;
import kr.co.yigil.comment.domain.CommentInfo.CommentsUnitInfo;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.TravelReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentReader commentReader;
    private final CommentStore commentStore;
    private final MemberReader memberReader;
    private final TravelReader travelReader;
    private final CommentCountCacheStore commentCountCacheStore;

    @Override
    @Transactional
    public CommentNotiInfo createComment(Long memberId, Long travelId,
        CommentCommand.CommentCreateRequest commentCreateRequest) {

        Member member = memberReader.getMember(memberId);
        Travel travel = travelReader.getTravel(travelId);
        Comment parentComment = commentReader.findComment(commentCreateRequest.getParentId()).orElse(null);
        var newComment = commentCreateRequest.toEntity(member, travel, parentComment);
        var savedComment = commentStore.save(newComment);

        commentCountCacheStore.increaseCommentCount(travelId);

        return new CommentNotiInfo(savedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long memberId, Long commentId) {
        Comment comment = commentReader.getCommentWithMemberId(commentId, memberId);
        commentStore.delete(comment);

        var travelId = commentReader.getTravelIdByCommentId(commentId);
        commentCountCacheStore.decreaseCommentCount(travelId);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentsResponse getParentComments(Long travelId, Pageable pageable) {

        Slice<Comment> comments = commentReader.getParentCommentsByTravelId(travelId,
            pageable);
        boolean hasNext = comments.hasNext();
        List<CommentsUnitInfo> commentsUnitInfoList = comments.getContent().stream()
            .map(comment -> {
                int childCount = commentReader.getChildrenCommentCount(comment.getId());
                return new CommentsUnitInfo(comment, childCount);
            }).toList();
        return new CommentsResponse(commentsUnitInfoList, hasNext);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentsResponse getChildComments(Long parentId, Pageable pageable) {
        Slice<Comment> comments = commentReader.getChildCommentsByParentId(parentId, pageable);
        return new CommentsResponse(comments);
    }

    @Override
    @Transactional
    public CommentNotiInfo updateComment(Long commentId, Long memberId, CommentUpdateRequest command) {
        Comment comment = commentReader.getCommentWithMemberId(commentId, memberId);
        comment.updateComment(command.getContent());

        return new CommentNotiInfo(comment);
    }
}
