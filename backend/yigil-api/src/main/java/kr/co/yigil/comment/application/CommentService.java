package kr.co.yigil.comment.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_COMMENT_ID;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.comment.domain.repository.CommentCountRepository;
import kr.co.yigil.comment.domain.repository.CommentRepository;
import kr.co.yigil.comment.dto.request.CommentCreateRequest;
import kr.co.yigil.comment.dto.response.CommentCreateResponse;
import kr.co.yigil.comment.dto.response.CommentDeleteResponse;
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.travel.Travel;
import kr.co.yigil.travel.application.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final NotificationService notificationService;
    private final CommentRedisIntegrityService commentRedisIntegrityService;
    private final CommentCountRepository commentCountRepository;
    private final TravelService travelService;

    @Transactional
    public CommentCreateResponse createComment(Long memberId, Long travelId, CommentCreateRequest commentCreateRequest) {
        Member member = memberService.findMemberById(memberId);
        Travel travel = travelService.findTravelById(travelId);

        Comment parentComment = null;
        if (commentCreateRequest.getParentId() != null && commentCreateRequest.getNotifiedMemberId() != null) {
            parentComment = findCommentById(commentCreateRequest.getParentId());
            Member notifiedMember = memberService.findMemberById(commentCreateRequest.getNotifiedMemberId());
            sendCommentNotification(notifiedMember, commentCreateRequest.getContent());
        }

        Comment newComment = new Comment(commentCreateRequest.getContent(), member, travel, parentComment);
        commentRedisIntegrityService.ensureCommentCount(travel);
        commentRepository.save(newComment);
        incrementCommentCount(travelId);

        return new CommentCreateResponse("댓글 생성 성공");
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentList(Long travelId) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        Travel travel = travelService.findTravelById(travelId);

        commentRepository.findTopLevelCommentsByTravelId(travelId)
            .forEach(comment -> {
                CommentResponse commentResponse = CommentResponse.from(comment);
                commentResponses.add(commentResponse);
                commentRepository.findRepliesByTravelIdAndParentId(travelId, comment.getId())
                    .forEach(reply -> {
                        CommentResponse replyResponse = CommentResponse.from(reply);
                        commentResponse.addChild(replyResponse);
                    });
            });

        commentRedisIntegrityService.ensureCommentCount(travel);
        return commentResponses;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getTopLevelCommentList(Long travelId) {

        List<CommentResponse> commentResponses = new ArrayList<>();
        List<Comment> comments = commentRepository.findTopLevelCommentsByTravelId(travelId);
        comments.stream()
            .map(CommentResponse::from)
            .forEach(commentResponses::add);

        return commentResponses;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getReplyCommentList(Long travelId, Long parentId) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        List<Comment> comments = commentRepository.findRepliesByTravelIdAndParentId(travelId, parentId);
        comments.stream()
            .map(CommentResponse::from)
            .forEach(commentResponses::add);
        return commentResponses;
    }

    @Transactional
    public CommentDeleteResponse deleteComment(Long memberId, Long travelId, Long commentId) {
        Travel travel = travelService.findTravelById(travelId);
        validateCommentWriter(memberId, commentId);
        Comment comment = findCommentById(commentId);

        commentRedisIntegrityService.ensureCommentCount(travel);
        commentRepository.delete(comment);
        decrementCommentCount(travelId);
        return new CommentDeleteResponse("댓글 삭제 성공");
    }

    private void incrementCommentCount(Long travelId) {
        commentCountRepository.findByTravelId(travelId)
            .ifPresent(CommentCount::incrementCommentCount);
    }

    private void decrementCommentCount(Long travelId) {
        commentCountRepository.findByTravelId(travelId)
            .ifPresent(CommentCount::decrementCommentCount);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_COMMENT_ID));
    }


    public void validateCommentWriter(Long memberId, Long commentId) {
        if (!commentRepository.existsByMemberIdAndId(memberId, commentId)) {
            throw new BadRequestException(ExceptionCode.INVALID_AUTHORITY);
        }
    }

    private void sendCommentNotification(Member notifiedMember, String content) {
        Notification notify = new Notification(notifiedMember, content);
        notificationService.sendNotification(notify);
    }
}

