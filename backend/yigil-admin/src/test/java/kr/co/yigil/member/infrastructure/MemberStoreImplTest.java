package kr.co.yigil.member.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import kr.co.yigil.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberStoreImplTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberStoreImpl memberStore;

    @DisplayName("banMember 메서드가 MemberRepository를 잘 호출하는지")
    @Test
    void banMember_ShouldCallRepository() {
        memberStore.banMember(1L);

        verify(memberRepository).banMemberById(1L);
    }

    @DisplayName("unbanMember 메서드가 MemberRepository를 잘 호출하는지")
    @Test
    void unbanMember_ShouldCallRepository() {
        memberStore.unbanMember(1L);

        verify(memberRepository).unbanMemberById(1L);
    }

}