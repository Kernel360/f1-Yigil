package kr.co.yigil.follow.dto.response;

import kr.co.yigil.follow.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerFindDto {
    private String memberImageUrl;
    private String memberNickName;
    private Long followingId;

    public static FollowerFindDto from(Follow follow) {
        return new FollowerFindDto(
                follow.getFollowing().getProfileImageUrl(),
                follow.getFollowing().getNickname(),
                follow.getFollowing().getId()
        );
    }
}
