package kr.co.yigil.member.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.presentation.SpotController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberUtils.class)
class MemberUtilsTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberUtils memberUtils;

    @Test
    void GivenMemberid_WhenFindMemberById_ThenReturnMember() {

        Long memberId = 2L;
        Member mockMember = new Member(memberId,"shin@gmail.com", "123456", "God", "profile.jpg",
            SocialLoginType.KAKAO);

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(mockMember));

        Member testmember = memberUtils.findMemberById(2L);
        assertThat(testmember).isEqualTo(mockMember);
        assertThat(testmember.getId()).isEqualTo(memberId);

    }
}