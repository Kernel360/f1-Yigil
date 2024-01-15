package kr.co.yigil.follow.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

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
