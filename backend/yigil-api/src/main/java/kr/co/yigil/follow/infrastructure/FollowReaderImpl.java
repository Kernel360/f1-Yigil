package kr.co.yigil.follow.infrastructure;

import kr.co.yigil.follow.FollowCountDto;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.FollowReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowReaderImpl implements FollowReader {

    private final FollowRepository followRepository;

    @Override
    public FollowCount getFollowCount(Long memberId) {
        FollowCountDto followCountDto = followRepository.getFollowCounts(memberId);
        return new FollowCount(memberId, followCountDto.getFollowerCount(),
            followCountDto.getFollowingCount());
    }

    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
    }

    @Override
    public Slice<Follow> getFollowerSlice(Long memberId, Pageable pageable) {
        return followRepository.findAllByFollowingId(memberId, pageable);
    }

    @Override
    public Slice<Follow> getFollowingSlice(Long memberId, Pageable pageable) {
        return followRepository.findAllByFollowerId(memberId, pageable);

    }
}
