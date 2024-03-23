package kr.co.yigil.member.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.MemberStatus;
import kr.co.yigil.member.interfaces.dto.request.MemberBanRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    private MemberReader memberReader;

    @Mock
    private MemberStore memberStore;

    @InjectMocks
    private MemberServiceImpl memberService;

    @DisplayName("getMemberPage 메서드가 MemberReader를 잘 호출하는지")
    @Test
    void getMemberPage_ShouldCallReader() {
        Pageable pageable = mock(Pageable.class);
        memberService.getMemberPage(pageable);

        verify(memberReader).getMemberPageRegardlessOfStatus(pageable);
    }

    @DisplayName("banMembers 메서드가 MemberReader와 MemberStore를 잘 호출하는지")
    @Test
    void banMembers_ShouldCallReaderAndStore() {
        MemberBanRequest request = new MemberBanRequest(List.of(1L, 2L, 3L));
        Member mockMember = mock(Member.class);
        when(mockMember.getStatus()).thenReturn(MemberStatus.ACTIVE);
        when(memberReader.getMemberRegardlessOfStatus(anyLong())).thenReturn(mockMember);

        memberService.banMembers(request);

        verify(memberReader,times(3)).getMemberRegardlessOfStatus(anyLong());
        verify(memberStore, times(3)).banMember(anyLong());
    }

    @DisplayName("banMembers 메서드가 이미 정지된 회원을 정지하려고 할 때 BadRequestException을 던지는지")
    @Test
    void banMembers_ShouldThrowBadRequestException_WhenMemberIsAlreadyBanned() {
        MemberBanRequest request = new MemberBanRequest(List.of(1L, 2L, 3L));
        Member mockMember = mock(Member.class);
        when(mockMember.getStatus()).thenReturn(MemberStatus.BANNED);
        when(memberReader.getMemberRegardlessOfStatus(anyLong())).thenReturn(mockMember);

        assertThrows(BadRequestException.class, () -> memberService.banMembers(request));
    }

    @DisplayName("unbanMembers 메서드가 MemberReader와 MemberStore를 잘 호출하는지")
    @Test
    void unbanMembers_ShouldCallReaderAndStore() {
        MemberBanRequest request = new MemberBanRequest(List.of(1L, 2L, 3L));
        Member mockMember = mock(Member.class);
        when(mockMember.getStatus()).thenReturn(MemberStatus.BANNED);
        when(memberReader.getMemberRegardlessOfStatus(anyLong())).thenReturn(mockMember);

        memberService.unbanMembers(request);

        verify(memberReader,times(3)).getMemberRegardlessOfStatus(anyLong());
        verify(memberStore, times(3)).unbanMember(anyLong());
    }

    @DisplayName("unbanMembers 메서드가 이미 활성화된 회원을 활성화하려고 할 때 BadRequestException을 던지는지")
    @Test
    void unbanMembers_ShouldThrowBadRequestException_WhenMemberIsAlreadyUnbanned() {
        MemberBanRequest request = new MemberBanRequest(List.of(1L, 2L, 3L));
        Member mockMember = mock(Member.class);
        when(mockMember.getStatus()).thenReturn(MemberStatus.ACTIVE);
        when(memberReader.getMemberRegardlessOfStatus(anyLong())).thenReturn(mockMember);

        assertThrows(BadRequestException.class, () -> memberService.unbanMembers(request));
    }

}