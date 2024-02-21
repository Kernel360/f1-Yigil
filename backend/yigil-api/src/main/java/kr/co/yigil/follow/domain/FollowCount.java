package kr.co.yigil.follow.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@AllArgsConstructor
@RedisHash("followCount")
public class FollowCount implements Serializable {

    @Id
    private Long memberId;

    private int followerCount;

    private int followingCount;

    public void incrementFollowerCount() {
        followerCount++;
    }

    public void decrementFollowerCount() {
        followerCount--;
    }

    public void incrementFollowingCount() {
        followingCount++;
    }

    public void decrementFollowingCount() {
        followingCount--;
    }

}
