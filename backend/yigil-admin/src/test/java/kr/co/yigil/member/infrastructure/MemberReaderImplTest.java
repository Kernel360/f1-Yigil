package kr.co.yigil.member.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class MemberReaderImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberReaderImpl memberReader;

    @DisplayName("getMemberRegardlessOfStatus 메서드가 MemberRepository를 잘 호출하는지")
    @Test
    void getMemberRegardlessOfStatus_ShouldCallRepository() {
        Member member = mock(Member.class);
        when(memberRepository.findByIdRegardlessOfStatus(1L)).thenReturn(Optional.of(member));

        Member memberRegardlessOfStatus = memberReader.getMemberRegardlessOfStatus(1L);

        assertEquals(member, memberRegardlessOfStatus);
        verify(memberRepository).findByIdRegardlessOfStatus(1L);
    }

    @DisplayName("getMemberPageRegardlessOfStatus 메서드가 MemberRepository를 잘 호출하는지")
    @Test
    void getMemberPageRegardlessOfStatus_ShouldCallRepository() {
        when(memberRepository.findAllMembersRegardlessOfStatus(any())).thenReturn(mock(Page.class));
        Page<Member> memberPageRegardlessOfStatus = memberReader.getMemberPageRegardlessOfStatus(
                mock(Pageable.class));

        assertNotNull(memberPageRegardlessOfStatus);
        verify(memberRepository).findAllMembersRegardlessOfStatus(any());
    }


}