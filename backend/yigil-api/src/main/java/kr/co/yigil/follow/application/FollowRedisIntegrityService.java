package kr.co.yigil.follow.application;

import jakarta.transaction.Transactional;
import java.util.Optional;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.repository.FollowCountRepository;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.follow.dto.FollowCountDto;
import kr.co.yigil.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowRedisIntegrityService {
    private final FollowRepository followRepository;
    private final FollowCountRepository followCountRepository;

    @Transactional
    public FollowCount ensureFollowCounts(Member member) {
        Long memberId = member.getId();
        Optional<FollowCount> existingFollowCount = followCountRepository.findById(memberId);
        if (existingFollowCount.isPresent()) {
            return existingFollowCount.get();
        } else {

            // else는 없어도 될것 같습니다!
            FollowCount followCount = getFollowCount(member);
            followCountRepository.save(followCount);
            return followCount;
        }
    }

    private FollowCount getFollowCount(Member member) {
        FollowCountDto followCountDto = followRepository.getFollowCounts(member);
        return new FollowCount(member.getId(), followCountDto.getFollowerCount(),
                followCountDto.getFollowingCount());
    }
}
