package kr.co.yigil.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowCountDto {
    private int followerCount;
    private int followingCount;
}
