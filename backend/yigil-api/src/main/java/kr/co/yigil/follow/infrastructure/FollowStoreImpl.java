package kr.co.yigil.follow.infrastructure;

import jakarta.transaction.Transactional;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.FollowStore;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowStoreImpl implements FollowStore {
    private final FollowRepository followRepository;

    @Override
    public void store(Member follower, Member following) {
        followRepository.save(new Follow(follower, following));

    }

    @Override
    public void remove(Member unfollower, Member unfollowing) {
        followRepository.deleteByFollowerAndFollowing(unfollower, unfollowing);

    }
}
