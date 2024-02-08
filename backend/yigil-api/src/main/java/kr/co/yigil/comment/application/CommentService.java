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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    /**
     * Service끼리 참조하게 되는경우 순환참조가 발생할듯 합니다 Service에서 같은 Layer의 Service를 참조하는것은 지양하는편이 좋습니다.
     * Data를 전달하는 Dataprovider와 같은 중간 Layer를 추가해보시는건 어떠세요 ?
     */
    private final MemberService memberService;
    private final NotificationService notificationService;
    private final CommentRedisIntegrityService commentRedisIntegrityService;
    private final TravelService travelService;

    @Transactional
    public CommentCreateResponse createComment(Long memberId, Long travelId, CommentCreateRequest commentCreateRequest) {
        Member member = memberService.findMemberById(memberId);
        Travel travel = travelService.findTravelById(travelId);

        Comment parentComment = null;

        /**
         * 유효성 체크의 역할을 하는 클래스를 만들어 관리하면 어떨까요 ??
         * ex) CommentValidate.createValidate(commentCreateRequest) 내부에 검증 구현  or
         * CommentValidate.createValidate(commentCreateRequest.getParentId(), commentCreateRequest.getNotifiedMemberId()) 와 같은식입니다.
         */
        if (commentCreateRequest.getParentId() != null && commentCreateRequest.getNotifiedMemberId() != null) {
            parentComment = findCommentById(commentCreateRequest.getParentId());
            Member notifiedMember = memberService.findMemberById(commentCreateRequest.getNotifiedMemberId());
            sendCommentNotification(notifiedMember, commentCreateRequest.getContent());
        }

        Comment newComment = new Comment(commentCreateRequest.getContent(), member, travel, parentComment);
        incrementCommentCount(travel);
        commentRepository.save(newComment);

        /**
         *  성공해 대한 내용을 String을 통해 반환해야할 필요가 있을까요??
         *  응답 코드를 통해 전달해주시는것은 어떠실까요??
         *  ex) {status:200 , message:"comment create"} 반환하더라도 Front에서 200에 대한 메시지를 정리하는것은 어떨까요?
         */
        return new CommentCreateResponse("댓글 생성 성공");
    }

    // ❌ 사용하지 않는 코드는 삭제!
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


    /**
     * private 으로 구현된 메서드에 대해서 Service <==> Repository간 데이터만 전달하는 class를 만들어서 처리하면 어떨까요?
     * private 메서드가 Service 코드에 있다면 테스트 코드 작성시 해당 메서드만 따로 테스트하기위해서는 복잡하지 않을까 의견드립니다.
     */
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

