package kr.co.yigil.follow.application;

import jakarta.transaction.Transactional;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.repository.FollowCountRepository;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.follow.dto.response.FollowResponse;
import kr.co.yigil.follow.dto.response.UnfollowResponse;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.global.exception.ExceptionResponse;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.repository.MemberRepository;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final FollowCountRepository followCountRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;
    private final FollowRedisIntegrityService followRedisIntegrityService;

    @Transactional
    public FollowResponse follow(final Long followerId, final Long followingId) {
        if (followerId.equals(followingId)) {
            return new FollowResponse("나 자신을 follow할 수 없습니다.");
        } else if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            return new FollowResponse("이미 follow 처리 되어 있습니다.");
        }
        Member follower = getMemberById(followerId);
        Member following = getMemberById(followingId);

        followRedisIntegrityService.ensureFollowCounts(follower);
        followRedisIntegrityService.ensureFollowCounts(following);

        followRepository.save(new Follow(follower, following));
        incrementFollowersCount(followingId);
        incrementFollowingsCount(followerId);

        sendFollowNotification(follower, following);

        return new FollowResponse("팔로우가 완료되었습니다.");
    }

    @Transactional
    public UnfollowResponse unfollow(final Long followerId, final Long followingId) {
        Member unfollower = getMemberById(followerId);
        Member unfollowing = getMemberById(followingId);

        followRedisIntegrityService.ensureFollowCounts(unfollower);
        followRedisIntegrityService.ensureFollowCounts(unfollowing);

        followRepository.deleteByFollowerAndFollowing(unfollower, unfollowing);
        decrementFollowersCount(followingId);
        decrementFollowingsCount(followerId);
        return new UnfollowResponse("팔로우가 취소되었습니다.");
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID));
    }

    private void sendFollowNotification(Member follower, Member following) {
        String message = follower.getNickname() + "님이 팔로우 하였습니다.";
        Notification notify = new Notification(following, message);
        notificationService.sendNotification(notify);
    }

    private void incrementFollowersCount(Long memberId) {
        followCountRepository.findById(memberId)
                .ifPresent(followCount -> {
                    followCount.incrementFollowerCount();
                    followCountRepository.save(followCount);
                });
    }

    private void decrementFollowersCount(Long memberId) {
        followCountRepository.findById(memberId)
                .ifPresent(followCount -> {
                    followCount.decrementFollowerCount();
                    followCountRepository.save(followCount);
                });
    }

    private void incrementFollowingsCount(Long memberId) {
        followCountRepository.findById(memberId)
                .ifPresent(followCount -> {
                    followCount.incrementFollowingCount();
                    followCountRepository.save(followCount);
                });
    }

    private void decrementFollowingsCount(Long memberId) {
        followCountRepository.findById(memberId)
                .ifPresent(followCount -> {
                    followCount.decrementFollowingCount();
                    followCountRepository.save(followCount);
                });
    }
}

