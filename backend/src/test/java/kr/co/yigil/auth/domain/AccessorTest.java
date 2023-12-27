package kr.co.yigil.auth.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccessorTest {

    @DisplayName("로그인 안한 Accessor 객체가 잘 생성되는지")
    @Test
    void testGuestAccessorCreation() {
        Accessor guest = Accessor.guest();
        assertNotNull(guest);
        assertEquals(Authority.GUEST, guest.getAuthority());
        assertEquals(0L, guest.getMemberId());
        assertFalse(guest.isMember());
    }

    @DisplayName("로그인한 Accessor 객체가 잘 생성되는지")
    @Test
    void testMemberAccessorCreation() {
        Long memberId = 1L;
        Accessor member = Accessor.member(memberId);
        assertNotNull(member);
        assertEquals(Authority.MEMBER, member.getAuthority());
        assertEquals(memberId, member.getMemberId());
        assertTrue(member.isMember());
    }

}
