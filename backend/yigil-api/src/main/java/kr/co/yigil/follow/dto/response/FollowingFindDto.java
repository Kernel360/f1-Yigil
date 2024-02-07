package kr.co.yigil.follow.dto.response;

import kr.co.yigil.follow.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowingFindDto {
        private String memberImageUrl;
        private String memberNickName;
        private Long followingId;

        public static FollowingFindDto from(Follow follow) {
            return new FollowingFindDto(
                follow.getFollowing().getProfileImageUrl(),
                follow.getFollowing().getNickname(),
                follow.getFollowing().getId()
            );
        }
}
