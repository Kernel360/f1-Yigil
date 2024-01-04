package kr.co.yigil.follow.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash("followCount")
public class FollowCount {

    @Id
    private Long memberId;

    private int followerCount;

    private int followingCount;
}
