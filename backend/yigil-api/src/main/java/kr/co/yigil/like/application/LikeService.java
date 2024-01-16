package kr.co.yigil.like.application;

import jakarta.transaction.Transactional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.like.domain.Like;
import kr.co.yigil.like.domain.repository.LikeCountRepository;
import kr.co.yigil.like.domain.repository.LikeRepository;
import kr.co.yigil.like.dto.response.LikeResponse;
import kr.co.yigil.like.dto.response.UnlikeResponse;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final MemberRepository memberRepository;
    private final PostService postService;
    private final LikeRepository likeRepository;
    private final LikeCountRepository likeCountRepository;
    private final NotificationService notificationService;
    private final LikeRedisIntegrityService likeRedisIntegrityService;

    @Transactional
    public LikeResponse like(final Long memberId, final Long postId) {
        Member member = getMemberById(memberId);
        Post post = postService.findPostById(postId);

        likeRedisIntegrityService.ensureLikeCounts(post);

        likeRepository.save(new Like(member, post));
        incrementLikesCount(postId);

        sendLikeNotification(post, member);

        return new LikeResponse("좋아요가 완료되었습니다.");
    }

    @Transactional
    public UnlikeResponse unlike(final Long memberId, final Long postId) {
        Member member = getMemberById(memberId);
        Post post = postService.findPostById(postId);

        likeRedisIntegrityService.ensureLikeCounts(post);

        likeRepository.deleteByMemberAndPost(member, post);
        decrementLikesCount(postId);
        return new UnlikeResponse("좋아요가 취소되었습니다.");
    }

    private void sendLikeNotification(Post post, Member member) {
        String message = member.getNickname() + "님이 게시글에 좋아요를 눌렀습니다.";
        Notification notify = new Notification(post.getMember(), message);
        notificationService.sendNotification(notify);
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID));
    }


    private void incrementLikesCount(Long postId) {
        likeCountRepository.findById(postId)
                .ifPresent(likeCount -> {
                    likeCount.incrementLikeCount();
                    likeCountRepository.save(likeCount);
                });
    }

    private void decrementLikesCount(Long postId) {
        likeCountRepository.findById(postId)
                .ifPresent(likeCount -> {
                    likeCount.decrementLikeCount();
                    likeCountRepository.save(likeCount);
                });
    }
}
