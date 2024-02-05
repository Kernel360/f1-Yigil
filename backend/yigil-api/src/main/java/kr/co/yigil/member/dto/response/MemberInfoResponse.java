package kr.co.yigil.member.dto.response;

import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoResponse {

    private String nickname;

    private String profileImageUrl;

    private int followerCount;

    private int followingCount;

    public static MemberInfoResponse from(final Member member, final FollowCount followCount) {
        return new MemberInfoResponse(member.getNickname(), member.getProfileImageUrl(), followCount.getFollowerCount(),
                followCount.getFollowingCount());
    }
}
