package kr.co.yigil.member.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberService;
import kr.co.yigil.member.interfaces.dto.request.MemberBanRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class MemberFacadeTest {
    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberFacade memberFacade;

    @DisplayName("getMemberPage 메서드가 MemberService를 잘 호출하는지")
    @Test
    void getMemberPage_ShouldCallService() {
        Pageable pageable = mock(Pageable.class);
        Page<Member> expectedPage = new PageImpl<>(Collections.emptyList());
        when(memberService.getMemberPage(pageable)).thenReturn(expectedPage);

        Page<Member> memberPage = memberFacade.getMemberPage(pageable);

        assertEquals(expectedPage, memberPage);
        verify(memberService).getMemberPage(pageable);
    }

    @DisplayName("banMembers 메서드가 MemberService를 잘 호출하는지")
    @Test
    void banMembers_ShouldCallService() {
        MemberBanRequest request = mock(MemberBanRequest.class);

        memberFacade.banMembers(request);

        verify(memberService).banMembers(request);
    }

    @DisplayName("unbanMembers 메서드가 MemberService를 잘 호출하는지")
    @Test
    void unbanMembers_ShouldCallService() {
        MemberBanRequest request = mock(MemberBanRequest.class);

        memberFacade.unbanMembers(request);

        verify(memberService).unbanMembers(request);
    }

}