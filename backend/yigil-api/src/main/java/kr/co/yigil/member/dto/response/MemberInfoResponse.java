package kr.co.yigil.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoResponse {

    private String nickname;

    private String profileImageUrl;

//    private List<Travel> travelList;

    private int followerCount;

    private int followingCount;

//    public static MemberInfoResponse from(final Member member, final List<Post> postList, final
//            FollowCount followCount) {
//        return new MemberInfoResponse(member.getNickname(), member.getProfileImageUrl(), postList, followCount.getFollowerCount(),
//                followCount.getFollowingCount());
//    }
}
