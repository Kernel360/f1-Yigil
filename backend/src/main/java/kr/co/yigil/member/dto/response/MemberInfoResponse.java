package kr.co.yigil.member.dto.response;

import java.util.List;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoResponse {

    private String nickname;

    private String profileImageUrl;

    private List<Post> postList;

    private int followerCount;

    private int followingCount;

    public static MemberInfoResponse from(final Member member, final List<Post> postList, final
            FollowCount followCount) {
        return new MemberInfoResponse(member.getNickname(), member.getProfileImageUrl(), postList, followCount.getFollowerCount(),
                followCount.getFollowingCount());
    }
}
