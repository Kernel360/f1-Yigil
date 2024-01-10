package kr.co.yigil.like.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;

import jakarta.transaction.Transactional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.like.domain.Like;
import kr.co.yigil.like.domain.repository.LikeCountRedisRepository;
import kr.co.yigil.like.domain.repository.LikeCountRepository;
import kr.co.yigil.like.domain.repository.LikeRepository;
import kr.co.yigil.like.dto.response.LikeResponse;
import kr.co.yigil.like.dto.response.UnlikeResponse;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRedisIntegrityService likeRedisIntegrityService;
    private final NotificationService notificationService;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final LikeCountRedisRepository likeCountRedisRepository;

    @Transactional
    public LikeResponse like(final Long memberId, final Long postId) {
        Post post = getPostById(postId);
        Member member = getMemberById(memberId);

        likeRedisIntegrityService.ensureLikeCount(post);

        likeRepository.save(new Like(member, post));
        likeCountRedisRepository.incrementLikeCount(postId);

        sendLikeNotification(member, post);

        return new LikeResponse("좋아요가 완료되었습니다.");
    }

    @Transactional
    public UnlikeResponse unlike(final Long memberId, final Long postId) {
        Post post = getPostById(postId);
        Member member = getMemberById(memberId);

        likeRedisIntegrityService.ensureLikeCount(post);

        likeRepository.deleteByMemberAndPost(member, post);
        likeCountRedisRepository.decrementLikeCount(postId);

        return new UnlikeResponse("좋아요가 취소되었습니다.");
    }

    private void sendLikeNotification(Member member, Post post) {
        String message = member.getNickname() + "님이 회원님의 게시물을 좋아합니다.";
        Notification notify = new Notification(post.getMember(), message);
        notificationService.sendNotification(notify);
    }

    private Post getPostById(Long postId) {
        // TODO: 현재 PR 머지될 시, NOT_FOUND_POST_ID 로 에러코드 변경
        return postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));
    }


}
