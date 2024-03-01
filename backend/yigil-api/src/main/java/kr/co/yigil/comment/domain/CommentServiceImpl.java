package kr.co.yigil.comment.domain;


import kr.co.yigil.comment.domain.CommentCommand.CommentCreateRequest;
import kr.co.yigil.comment.domain.CommentCommand.CommentUpdateRequest;
import kr.co.yigil.comment.domain.CommentInfo.CommentsResponse;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.TravelReader;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
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
    public CommentInfo.CommentCreateResponse createComment(Long memberId, Long travelId,
        CommentCommand.CommentCreateRequest commentCreateRequest) {

        Member member = memberReader.getMember(memberId);
        Travel travel = travelReader.getTravel(travelId);
        Comment parentComment = getParentComment(commentCreateRequest);

        var newComment = commentCreateRequest.toEntity(member, travel, parentComment);
        commentStore.save(newComment);

        commentCountCacheStore.increaseCommentCount(travelId);
        return new CommentInfo.CommentCreateResponse("댓글 생성 성공");
    }

    @Override
    @Transactional
    public void deleteComment(Long memberId, Long commentId) {
        var travelId = commentReader.getTravelIdByCommentId(commentId);
        Comment comment = commentReader.getCommentByMemberId(commentId, memberId);
        commentStore.delete(comment);
        commentCountCacheStore.decreaseCommentCount(travelId);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentsResponse getParentComments(Long travelId, Pageable pageable) {
        Slice<Comment> comments = commentReader.getParentCommentsByTravelId(travelId,
            pageable);
        return new CommentsResponse(comments);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentsResponse getChildComments(Long parentId, Pageable pageable) {
        Slice<Comment> comments = commentReader.getChildCommentsByParentId(parentId, pageable);
        return new CommentsResponse(comments);
    }

    @Override
    @Transactional
    public CommentInfo.UpdateResponse updateComment(Long commentId, Long memberId, CommentUpdateRequest command) {
        Comment comment = commentReader.getCommentByMemberId(commentId, memberId);
        Long travelId = commentReader.getTravelIdByCommentId(commentId);
        comment.updateComment(command.getContent());
        return new CommentInfo.UpdateResponse(travelId);
    }

    @Nullable
    private Comment getParentComment(CommentCreateRequest commentCreateRequest) {
        if (commentCreateRequest.getParentId() == null) {
            return null;
        }
        return commentReader.getComment(commentCreateRequest.getParentId());
    }
}
