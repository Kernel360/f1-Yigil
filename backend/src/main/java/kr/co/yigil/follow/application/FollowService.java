package kr.co.yigil.follow.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;

import jakarta.transaction.Transactional;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.repository.FollowCountRedisRepository;
import kr.co.yigil.follow.domain.repository.FollowCountRepository;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.follow.dto.response.FollowResponse;
import kr.co.yigil.follow.dto.response.UnfollowResponse;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final FollowCountRedisRepository followCountRedisRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    @Transactional
    public FollowResponse follow(final Long followerId, final Long followingId) {
        Member follower = getMemberById(followerId);
        Member following = getMemberById(followingId);

        followRepository.save(new Follow(follower, following));
        followCountRedisRepository.incrementFollowersCount(followingId);
        followCountRedisRepository.incrementFollowingsCount(followerId);

        sendFollowNotification(follower, following);

        return new FollowResponse("팔로우가 완료되었습니다.");
    }

    @Transactional
    public UnfollowResponse unfollow(final Long followerId, final Long followingId) {
        Member unfollower = getMemberById(followerId);
        Member unfollowing = getMemberById(followingId);

        followRepository.deleteByFollowerAndFollowing(unfollower, unfollowing);
        followCountRedisRepository.decrementFollowersCount(followingId);
        followCountRedisRepository.decrementFollowingsCount(followerId);
        return new UnfollowResponse("팔로우가 취소되었습니다.");
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));
    }

    private void sendFollowNotification(Member follower, Member following) {
        String message = follower.getNickname() + "님이 팔로우 하였습니다.";
        Notification notify = new Notification(following, message);
        notificationService.sendNotification(notify);
    }
}

