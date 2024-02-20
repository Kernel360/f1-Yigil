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
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.application.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentList(Long travelId) {
        List<CommentResponse> commentResponses = new ArrayList<>();

        commentRepository.findParentCommentsByTravelId(travelId, Pageable.unpaged())
            .forEach(comment -> {
                CommentResponse commentResponse = CommentResponse.from(comment);
                commentResponses.add(commentResponse);
                commentRepository.findChildCommentsByParentId(comment.getId(), Pageable.unpaged())
                    .forEach(reply -> {
                        CommentResponse replyResponse = CommentResponse.from(reply);
                        commentResponse.addChild(replyResponse);
                    });
            });

        return  commentResponses;
    }

    @Transactional(readOnly = true)
    public Slice<CommentResponse> getParentCommentList(Long travelId, Pageable pageable) {
        Slice<Comment> comments = commentRepository.findParentCommentsByTravelId(travelId, pageable);
        List<CommentResponse> commentResponses = comments.stream().map(CommentResponse::from)
                .toList();

        return new SliceImpl<>(commentResponses, pageable, comments.hasNext());
    }

    @Transactional(readOnly = true)
    public Slice<CommentResponse> getChildCommentList(Long parentId, Pageable pageable) {
        Slice<Comment> comments = commentRepository.findChildCommentsByParentId(parentId, pageable);
        List<CommentResponse> commentResponses = comments.stream().map(CommentResponse::from)
                .toList();
        return new SliceImpl<>(commentResponses, pageable, comments.hasNext());
    }

    @Transactional
    public CommentDeleteResponse deleteComment(Long memberId, Long travelId, Long commentId) {
        Travel travel = travelService.findTravelById(travelId);
        decrementCommentCount(travel);

        Comment comment = findCommentByIdAndMemberId(commentId, memberId);
        commentRepository.delete(comment);
        return new CommentDeleteResponse("댓글 삭제 성공");
    }

    private Comment findCommentById(Long parentId) {
        return commentRepository.findById(parentId)
            .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_COMMENT_ID));
    }

    private void incrementCommentCount(Travel travel) {
        CommentCount commentCount = commentRedisIntegrityService.ensureCommentCount(travel);
        commentCount.incrementCommentCount();
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

