package kr.co.yigil.member.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberReaderImplTest {

    @InjectMocks
    private MemberReaderImpl memberReader;

    @Mock
    private MemberRepository memberRepository;

    @DisplayName("getMember 메서드가 올바른 Member를 반환하는지")
    @Test
    void WhenGetMember_ThenShouldReturnMember() {
        Long memberid = 1L;
        Member member = mock(Member.class);

        when(memberRepository.findById(memberid)).thenReturn(Optional.of(member));

        var result = memberReader.getMember(memberid);

        assertThat(result).isEqualTo(member);
    }

    @DisplayName("validateMember 메서드가 올바른 응답을 반환하는지")
    @Test
    void WhenValidateMember_ThenShouldNotOccuredErrror() {
        Long memberid = 1L;
        when(memberRepository.existsById(memberid)).thenReturn(true);

        memberReader.validateMember(memberid);
    }

    @DisplayName("findMemberBySocialLoginIdAndSocialLoginType 메서드가 올바른 응답을 반환하는지")
    @Test
    void WhenFindMemberBySocialLoginIdAndSocialLoginType_ThenShouldReturnMember() {
        String socialLoginId = "socialLoginId";
        var socialLoginType = mock(SocialLoginType.class);
        Member member = mock(Member.class);

        when(memberRepository.findMemberBySocialLoginIdAndSocialLoginType(socialLoginId, socialLoginType)).thenReturn(Optional.of(member));

        var result = memberReader.findMemberBySocialLoginIdAndSocialLoginType(socialLoginId, socialLoginType);

        assertThat(result).isEqualTo(Optional.of(member));
    }

    @DisplayName("findMemberByEmailAndSocialLoginType 메서드가 올바른 응답을 반환하는지")
    @Test
    void whenFindMemberByEmailAndSocialLoginType_ThenShouldReturnMember() {
        String email = "email";
        var socialLoginType = mock(SocialLoginType.class);
        Member member = mock(Member.class);

        when(memberRepository.findMemberByEmailAndSocialLoginType(email,
                socialLoginType)).thenReturn(Optional.of(member));

        var result = memberReader.findMemberByEmailAndSocialLoginType(email, socialLoginType);

        assertThat(result).isEqualTo(Optional.of(member));
    }

    @DisplayName("existsByNickname 메서드가 올바른 응답을 반환하는지")
	@Test
	void whenExistsByNickname_thenShouldReturnBoolean() {
        String nickname = "nickname";
        when(memberRepository.existsByNickname(nickname)).thenReturn(true);

        var result = memberReader.existsByNickname(nickname);

        assertThat(result).isTrue();
	}
}