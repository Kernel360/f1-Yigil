package kr.co.yigil.member.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
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

    @DisplayName("gvalidateMember 메서드가 올바른 응답을 반환하는지")
    @Test
    void WhenValidateMember_ThenShouldNotOccuredErrror() {

        Long memberid = 1L;
        when(memberRepository.existsById(memberid)).thenReturn(true);

        memberReader.validateMember(memberid);
        
    }
}