package kr.co.yigil.follow.domain;

import jakarta.transaction.Transactional;
import kr.co.yigil.follow.domain.FollowInfo.FollowerInfo;
import kr.co.yigil.follow.domain.FollowInfo.FollowersResponse;
import kr.co.yigil.follow.domain.FollowInfo.FollowingInfo;
import kr.co.yigil.follow.domain.FollowInfo.FollowingsResponse;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowReader followReader;
    private final MemberReader memberReader;
    private final FollowStore followStore;
    private final FollowCacheStore followCacheStore;

    @Override
    @Transactional
    public void follow(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new BadRequestException(ExceptionCode.FOLLOW_MYSELF);
        } else if (followReader.isFollowing(followerId, followingId)) {
            throw new BadRequestException(ExceptionCode.ALREADY_FOLLOWING);
        }

        Member follower = memberReader.getMember(followerId);
        Member following = memberReader.getMember(followingId);

        followStore.store(follower, following);
        followCacheStore.incrementFollowersCount(followingId);
        followCacheStore.incrementFollowingsCount(followerId);

    }

    @Override
    @Transactional
    public void unfollow(Long unfollowerId, Long unfollowingId) {
        if (unfollowerId.equals(unfollowingId)) {
            throw new BadRequestException(ExceptionCode.UNFOLLOW_MYSELF);
        } else if (!followReader.isFollowing(unfollowerId, unfollowingId)) {
            throw new BadRequestException(ExceptionCode.NOT_FOLLOWING);
        }
        Member unfollower = memberReader.getMember(unfollowerId);
        Member unfollowing = memberReader.getMember(unfollowingId);

        followStore.remove(unfollower, unfollowing);
        followCacheStore.decrementFollowersCount(unfollowingId);
        followCacheStore.decrementFollowingsCount(unfollowerId);
    }

    @Override
    @Transactional
    public FollowInfo.FollowersResponse getFollowerList(Long memberId, Pageable pageable) {
        memberReader.validateMember(memberId);
        var followerSlice = followReader.getFollowerSlice(memberId, pageable);
        var followerList = followerSlice.getContent().stream()
            .map(Follow::getFollower)
            .map(follower -> new FollowerInfo(follower, followReader.isFollowing(memberId, follower.getId())))
            .toList();
        return new FollowersResponse(followerList, followerSlice.hasNext());
    }

    @Override
    @Transactional
    public FollowingsResponse getFollowingList(Long memberId, Pageable pageable) {
        memberReader.validateMember(memberId);
        var followingSlice = followReader.getFollowingSlice(memberId, pageable);
        var followingList = followingSlice.getContent().stream()
            .map(Follow::getFollowing)
            .map(FollowingInfo::new)
            .toList();
        return new FollowingsResponse(followingList, followingSlice.hasNext());
    }
}
