package kr.co.yigil.follow.infrastructure;

import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.FollowReader;
import kr.co.yigil.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowReaderImpl implements FollowReader {

    private final FollowRepository followRepository;
    private final MemberReader memberReader;

    @Override
    public FollowCount getFollowCount(Long memberId) {
        memberReader.validateMember(memberId);
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
        memberReader.validateMember(memberId);
        return followRepository.findAllByFollowingId(memberId, pageable);
    }

    @Override
    public Slice<Follow> getFollowingSlice(Long memberId, Pageable pageable) {
        memberReader.validateMember(memberId);
        return followRepository.findAllByFollowerId(memberId, pageable);

    }
}
