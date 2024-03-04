package kr.co.yigil.member.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberCommand;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.domain.MemberService;
import kr.co.yigil.region.domain.Region;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberFacadeTest {

    @InjectMocks
    private MemberFacade memberFacade;

    @Mock
    private MemberService memberService;

    @DisplayName("getMemberInfo 메서드가 유효한 요청이 들어왔을 때 MemberInfo의 Main 객체를 잘 반환하는지")
    @Test
    void WhenGetMemberInfo_ThenShouldReturnMemberInfoMain() {
        // Given
        Long memberId = 1L;
        String email = "member1@email.com";
        String socialLoginId = "12345";
        String nickname = "member1";
        String profileImageUrl = "http://profile.com/member1";

        List<Region> regions = List.of(
            new Region(),
            new Region()
        );
        int followingCount = 10;
        int followerCount = 20;

        Member mockMember = new Member(
            memberId,
            email,
            socialLoginId,
            nickname,
            profileImageUrl,
            SocialLoginType.KAKAO
        );
        FollowCount mockFollowCount = new FollowCount(
            1L,
            followingCount,
            followerCount
        );

        MemberInfo.Main mockMemberInfoMain = new MemberInfo.Main(mockMember, mockFollowCount);

        when(memberService.retrieveMemberInfo(memberId)).thenReturn(mockMemberInfoMain);

        // When
        var result = memberFacade.getMemberInfo(memberId);

        // Then
        assertThat(result).isNotNull()
            .isInstanceOf(MemberInfo.Main.class)
            .usingRecursiveComparison().isEqualTo(mockMemberInfoMain);
    }

    @DisplayName("updateMemberInfo 메서드가 유효한 요청이 들어왔을 때 MemberService의 updateMemberInfo 메서드를 잘 호출하는지")
    @Test
    void WhenUpdateMemberInfo_ThenShouldValidResponse() {

        MemberCommand.MemberUpdateRequest command = mock(MemberCommand.MemberUpdateRequest.class);
        Long memberId = 1L;

        var result = memberFacade.updateMemberInfo(memberId, command);
        verify(memberService).updateMemberInfo(memberId, command);

        assertThat(result).isNotNull()
            .isInstanceOf(MemberInfo.MemberUpdateResponse.class)
            .usingRecursiveComparison().isEqualTo(new MemberInfo.MemberUpdateResponse("회원 정보 업데이트 성공"));
    }

    @DisplayName("withdraw 메서드가 유효한 요청이 들어왔을 때 MemberService의 withdrawal 메서드를 잘 호출하는지")
    @Test
    void WhenWithdraw_ThenShouldReturnValidDleteResponse() {
        Long memberId = 1L;

        var result = memberFacade.withdraw(memberId);
        verify(memberService).withdrawal(memberId);

        assertThat(result).isNotNull()
            .isInstanceOf(MemberInfo.MemberDeleteResponse.class)
            .usingRecursiveComparison().isEqualTo(new MemberInfo.MemberDeleteResponse("회원 탈퇴 성공"));
    }
}