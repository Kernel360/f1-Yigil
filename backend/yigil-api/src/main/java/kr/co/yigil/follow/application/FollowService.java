package kr.co.yigil.follow.application;

import jakarta.transaction.Transactional;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.repository.FollowCountRepository;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.follow.dto.response.FollowResponse;
import kr.co.yigil.follow.dto.response.UnfollowResponse;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
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

        /**
         * 유효성 검사 역할을 하는 클래스를 추가하여 Exception으로 처리하면 어떨까요?
         * 아래는 예시입니다.
         *
         *  // 내부에서 Exception 발생
         *  FollowValidation.selfFollowValidate(followerId, followingId);
         *  FollowValidation.alreadyFollowValidate(followerId, follwingId);
         * */
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

        /**
         * sendFollowNotification 이 대부분 이벤트 호출용으로 발송된다면
         * TransactionalEventListener을 사용하여 비동기로 처리하는것도 좋아보입니다.
         * */
        sendFollowNotification(follower, following);

        /** 이전 코멘트와 동일합니다. 분리! */
        return new FollowResponse("팔로우가 완료되었습니다.");
    }

    @Transactional
    public UnfollowResponse unfollow(final Long followerId, final Long followingId) {
        Member unfollower = getMemberById(followerId);
        Member unfollowing = getMemberById(followingId);

        /**
         * 현재 follwerId와 followingId가 팔로우 하고있는 상태인지 확인이 필요해 보입니다!
         *
         * 눈으로 밖에 확인하지 못했지만 서로 팔로우중이지 않은 값을 넣어준다면 해당 로직이 동작할것 같습니다.
         * */

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

