package kr.co.yigil.comment.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_COMMENT_ID;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.domain.CommentCount;
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
        incrementCommentCount(travel);
        commentRepository.save(newComment);

        return new CommentCreateResponse("댓글 생성 성공");
    }

    private Comment findCommentById(Long parentId) {
        return commentRepository.findById(parentId)
            .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_COMMENT_ID));
    }

    private void incrementCommentCount(Travel travel) {
        CommentCount commentCount = commentRedisIntegrityService.ensureCommentCount(travel);
        commentCount.incremenCommentCount();
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentList(Long travelId) {
        List<CommentResponse> commentResponses = new ArrayList<>();

        commentRepository.findParentCommentsByTravelId(travelId)
            .forEach(comment -> {
                CommentResponse commentResponse = CommentResponse.from(comment);
                commentResponses.add(commentResponse);
                commentRepository.findChildCommentsByTravelIdAndParentId(travelId, comment.getId())
                    .forEach(reply -> {
                        CommentResponse replyResponse = CommentResponse.from(reply);
                        commentResponse.addChild(replyResponse);
                    });
            });

        return  commentResponses;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getParentCommentList(Long travelId) {

        List<CommentResponse> commentResponses = new ArrayList<>();
        List<Comment> comments = commentRepository.findParentCommentsByTravelId(travelId);
        comments.stream()
            .map(CommentResponse::from)
            .forEach(commentResponses::add);

        return commentResponses;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getChildCommentList(Long travelId, Long parentId) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        List<Comment> comments = commentRepository.findChildCommentsByTravelIdAndParentId(travelId, parentId);
        comments.stream()
            .map(CommentResponse::from)
            .forEach(commentResponses::add);
        return commentResponses;
    }

    @Transactional
    public CommentDeleteResponse deleteComment(Long memberId, Long travelId, Long commentId) {
        Travel travel = travelService.findTravelById(travelId);
        decrementCommentCount(travel);

        Comment comment = findCommentByIdAndMemberId(commentId, memberId);
        commentRepository.delete(comment);
        return new CommentDeleteResponse("댓글 삭제 성공");
    }

    private void decrementCommentCount(Travel travel) {
        CommentCount commentcount = commentRedisIntegrityService.ensureCommentCount(travel);
        commentcount.decrementCommentCount();
    }

    private Comment findCommentByIdAndMemberId(Long commentId, Long memberId) {
        return commentRepository.findByIdAndMemberId(commentId, memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_COMMENT_ID));
    }

    private void sendCommentNotification(Member notifiedMember, String content) {
        Notification notify = new Notification(notifiedMember, content);
        notificationService.sendNotification(notify);
    }
}

