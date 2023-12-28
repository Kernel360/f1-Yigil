package kr.co.yigil.follow.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.query.Param;

@Getter
@RedisHash("followCount")
public class FollowCount {

    @Id
    private Long memberId;

    private final int followerCount;

    private final int followingCount;

    public FollowCount(@Param("memberId") Long memberId,
            @Param("followerCount") int followerCount,
            @Param("followingCount") int followingCount) {
        this.memberId = memberId;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }
}
