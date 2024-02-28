package kr.co.yigil.member.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class MemberStoreImplTest {
    @InjectMocks
    private MemberStoreImpl memberStore;

    @Mock
    private MemberRepository memberRepository;


    @DisplayName("deleteMember 메서드가 올바르게 동작하는지")
    @Test
    void WhenDeleteMember_ThenShouldNotThrowError() {
        Long memberId = 1L;

        memberStore.deleteMember(memberId);

        verify(memberRepository).deleteById(memberId);

    }

    @DisplayName("save 메서드가 올바르게 동작하는지")
    @Test
    void WhenMemberStoreSave_ThenShouldReturnSavedMember() {
        Member member = mock(Member.class);

        when(memberRepository.save(member)).thenReturn(member);

        var result = memberStore.save(member);

        assertThat(result).isEqualTo(member);
    }
}