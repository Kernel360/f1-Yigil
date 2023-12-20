package kr.co.yigil.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.member.dto.response.MemberInfoResponse;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("유효한 사용자ID가 주어졌을 때 사용자 정보가 잘 반환되는지")
    @Test
    void whenGetMemberInfo_thenReturnsMemberInfoResponse_withValidMemberInfo() {
        Long memberId = 1L;
        Member mockMember = new Member("kiit0901@gmail.com", "123456", "stone", "profile.jpg", "kakao");
        List<Post> mockPostList = new ArrayList<>();

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));
        when(postRepository.findAllByMember(mockMember)).thenReturn(mockPostList);

        MemberInfoResponse response = memberService.getMemberInfo(memberId);

        assertThat(response).isNotNull();
        assertThat(response.getNickname()).isEqualTo("stone");
        assertThat(response.getProfileImageUrl()).isEqualTo("profile.jpg");
    }

    @DisplayName("잘못된 사용자ID가 주어졌을 때 예외가 잘 발생하는지")
    @Test
    void whenGetMemberInfo_thenThrowsException_withInvalidMemberInfo() {
        Long invalidMemberId = 2L;

        when(memberRepository.findById(invalidMemberId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> memberService.getMemberInfo(invalidMemberId));
    }
}
