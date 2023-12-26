package kr.co.yigil.follow.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FollowTest {

    @DisplayName("팔로우 엔티티가 잘 생성되고 필드에 잘 접근되는지")
    @Test
    void createAndAccessFollow() {
       Member follower = new Member(1L, "follower", "123456", "follower", "image.png", SocialLoginType.KAKAO);
       Member following = new Member(2L, "following", "654321", "following", "profile.jpg", SocialLoginType.KAKAO);

       Follow follow = new Follow(follower, following);

       assertNotNull(follow);
       assertEquals(follower, follow.getFollower());
       assertEquals(following, follow.getFollowing());
    }

}
