package kr.co.yigil.follow.infrastructure;

import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.FollowReader;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.follow.dto.FollowCountDto;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FollowReaderImpl implements FollowReader {
    private final FollowRepository followRepository;
    private final MemberReader memberReader;

    @Override
    public FollowCount getFollowCount(Long memberId) {
        Member member = memberReader.getMember(memberId);
        FollowCountDto followCountDto = followRepository.getFollowCounts(member);
        return new FollowCount(memberId, followCountDto.getFollowerCount(), followCountDto.getFollowingCount());
    }

    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
    }


}
