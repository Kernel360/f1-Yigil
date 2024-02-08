package kr.co.yigil.follow.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// ValueObject용과 Request, Response는  따로 분리되어야 할듯합니다.
@Data
@NoArgsConstructor
public class FollowCountDto {
    private int followerCount;
    private int followingCount;

    public FollowCountDto(Long followerCount, Long followingCount) {
        this.followerCount = followerCount != null ? followerCount.intValue() : 0;
        this.followingCount = followingCount != null ? followingCount.intValue() : 0;
    }
}
