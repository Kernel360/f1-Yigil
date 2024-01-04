package kr.co.yigil.follow.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FollowCountTest {

    @DisplayName("FollowCount 엔티티가 잘 생성되고 접근 가능한지")
    @Test
    void createAndAccessFollowCount() {
        Long memberId = 1L;
        int followerCount = 10;
        int followingCount = 6;
        FollowCount followCount = new FollowCount(memberId, followerCount, followingCount);

        assertEquals(memberId, followCount.getMemberId());
        assertEquals(followerCount, followCount.getFollowerCount());
        assertEquals(followingCount, followCount.getFollowingCount());
    }

}