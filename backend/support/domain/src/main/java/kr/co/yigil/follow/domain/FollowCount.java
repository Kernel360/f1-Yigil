package kr.co.yigil.follow.domain;

import java.io.Serializable;
import java.util.Objects;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowCount that = (FollowCount) o;
        return followerCount == that.followerCount &&
                followingCount == that.followingCount &&
                Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, followerCount, followingCount);
    }
}
