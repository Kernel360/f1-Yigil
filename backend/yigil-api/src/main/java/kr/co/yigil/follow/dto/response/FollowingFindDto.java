package kr.co.yigil.follow.dto.response;

import kr.co.yigil.follow.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 필요한 롬복 어노테이션만 사용하면 어떨까요 ?*/
/** 네이밍의 변경이 필요해 보입니다 */
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
