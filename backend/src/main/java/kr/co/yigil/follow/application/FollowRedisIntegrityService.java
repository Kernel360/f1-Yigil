package kr.co.yigil.follow.application;

import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.follow.dto.FollowCountDto;
import kr.co.yigil.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowRedisIntegrityService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final FollowRepository followRepository;

    public void ensureFollowCounts(Member member) {
        Long memberId = member.getId();
        Boolean exists = redisTemplate.hasKey("followCount:" + memberId);
        if(Boolean.FALSE.equals(exists)) {
            FollowCount followCount = getFollowCount(member);
            redisTemplate.opsForValue().set("followCount:" + memberId, followCount);
        }
    }

    private FollowCount getFollowCount(Member member) {
        FollowCountDto followCountDto = followRepository.getFollowCounts(member);
        return new FollowCount(member.getId(), followCountDto.getFollowerCount(),
                followCountDto.getFollowingCount());
    }
}
