package kr.co.yigil.comment.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_COMMENT_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.post.domain.Post;
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
    private final PostService postService;

    @Transactional
    public CommentCreateResponse createComment(Long memberId, Long postId, CommentCreateRequest commentCreateRequest) {
        Member member = memberService.findMemberById(memberId);
        Post post = postService.findPostById(postId);

        Comment newComment = new Comment(commentCreateRequest.getContent(), member, post);

        if (commentCreateRequest.getParentId() != null && commentCreateRequest.getNotifiedMemberId() != null) {
            Comment parentComment = findCommentById(commentCreateRequest.getParentId());
            newComment.setParent(parentComment);

            Member notifiedMember = memberService.findMemberById(commentCreateRequest.getNotifiedMemberId());
            sendCommentNotification(notifiedMember, newComment.getContent());
        }
        commentRedisIntegrityService.ensureCommentCount(post);
        commentRepository.save(newComment);
        incrementCommentCount(postId);

        return new CommentCreateResponse("댓글 생성 성공");
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentList(Long postId) {
        List<Comment> comments = commentRepository.findCommentListByPostId(postId);

        List<CommentResponse> commentResponses = new ArrayList<>();
        Map<Long, CommentResponse> commentResponseMap = new HashMap<>();

        comments.forEach(comment -> {
            CommentResponse commentResponse = CommentResponse.from(comment);
            commentResponseMap.put(commentResponse.getId(), commentResponse);
            if (comment.getParent() != null) {
                commentResponseMap.get(comment.getParent().getId()).getChildren().add(commentResponse);
            } else {
                commentResponses.add(commentResponse);
            }
        });
        // comment count를 위한 redis cache
        commentRedisIntegrityService.ensureCommentCount(postService.findPostById(postId));
        // post의 comment count를 위한 redis cache

        return commentResponses;
    }

    @Transactional
    public CommentDeleteResponse deleteComment(Long memberId, Long postId, Long commentId) {
        Post post = postService.findPostById(postId);
        validateCommentWriter(memberId, commentId);
        Comment comment = findCommentById(commentId);

        int deletedCommentCount = 1 + comment.getChildren().size();
        commentRedisIntegrityService.ensureCommentCount(post);
        commentRepository.delete(comment);
        decrementCommentCount(postId, deletedCommentCount);
        return new CommentDeleteResponse("댓글 삭제 성공");
    }

    private void incrementCommentCount(Long postId) {
        commentCountRepository.findByPostId(postId)
            .ifPresent(CommentCount::incrementCommentCount);
    }

    private void decrementCommentCount(Long postId, int deletedCommentCount) {
        commentCountRepository.findByPostId(postId)
            .ifPresent(commentCount ->
                commentCount.decrementCommentCount(deletedCommentCount)
            );
    }

    @Transactional(readOnly = true)
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_COMMENT_ID));
    }

    @Transactional(readOnly = true)
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

