package kr.co.yigil.member.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileType;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberCommand;
import kr.co.yigil.member.domain.MemberCommand.VisibilityChangeRequest;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.domain.MemberInfo.CourseInfo;
import kr.co.yigil.member.domain.MemberInfo.CourseListResponse;
import kr.co.yigil.member.domain.MemberService;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.LineString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class MemberFacadeTest {

    @InjectMocks
    private MemberFacade memberFacade;

    @Mock
    private MemberService memberService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @DisplayName("getMemberInfo 메서드가 유효한 요청이 들어왔을 때 MemberInfo의 Main 객체를 잘 반환하는지")
    @Test
    void WhenGetMemberInfo_ThenShouldReturnMemberInfoMain() {
        // Given
        Long memberId = 1L;
        String email = "member1@email.com";
        String socialLoginId = "12345";
        String nickname = "member1";
        String profileImageUrl = "http://profile.com/member1";
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

    @DisplayName("getMemberCourseInfo 메서드가 유효한 요청이 들어왔을 때 MemberInfo의 MemberCourseResponse 객체를 잘 반환하는지")
    @Test
    void WhenGetMemberCourseInfo_ThenShouldReturnValidMemberCourseResponse() {
        // Given
        Long memberId = 1L;
        int totalPages = 1;
        PageRequest pageable = PageRequest.of(0, 5);

        String email = "test@test.com";
        String socialLoginId = "12345";
        String nickname = "tester";
        String profileImageUrl = "test.jpg";
        Member member = new Member(memberId, email, socialLoginId, nickname, profileImageUrl,
            SocialLoginType.KAKAO, Ages.NONE, Gender.NONE);

        Long courseId = 1L;
        String title = "Test Course Title";
        double rate = 5.0;
        LineString path = null;
        boolean isPrivate = false;
        List<Spot> spots = Collections.emptyList();
        int representativeSpotOrder = 0;
        AttachFile mapStaticImageFile = new AttachFile(FileType.IMAGE, "test.jpg", "test.jpg", 10L);

        Course mockCourse = new Course(courseId, member, title, null, rate, path, isPrivate,
            spots, representativeSpotOrder, mapStaticImageFile);

        MemberInfo.CourseInfo courseInfo = new MemberInfo.CourseInfo(mockCourse);
        List<CourseInfo> courseList = Collections.singletonList(courseInfo);

        MemberInfo.CourseListResponse mockCourseListResponse = new CourseListResponse(
            courseList,
            totalPages
        );

        when(memberService.retrieveCourseList(anyLong(), any(Pageable.class), anyString())).thenReturn(
            mockCourseListResponse);

        // When
        var result = memberFacade.getMemberCourseInfo(memberId, pageable, "private");

        // Then
        assertThat(result).isNotNull()
            .isInstanceOf(CourseListResponse.class)
            .usingRecursiveComparison().isEqualTo(mockCourseListResponse);
        assertThat(result.getContent().size()).isEqualTo(1);
    }

    @DisplayName("getMemberSpotInfo 메서드가 유효한 요청이 들어왔을 때 MemberInfo의 MemberSpotResponse 객체를 잘 반환하는지")
    @Test
    void WhenGetMemberSpotInfo_ThenShouldReturnValidMemberSpotResponse() {
        // Given
        Long memberId = 1L;
        int totalPages = 1;
        PageRequest pageable = PageRequest.of(0, 5);

        String email = "test@test.com";
        String socialLoginId = "12345";
        String nickname = "tester";
        String profileImageUrl = "test.jpg";
        Member member = new Member(memberId, email, socialLoginId, nickname, profileImageUrl,
            SocialLoginType.KAKAO, Ages.NONE, Gender.NONE);

        Long spotId = 1L;
        String title = "Test Spot Title";
        double rate = 5.0;
        AttachFile imageFile = new AttachFile(FileType.IMAGE, "test.jpg", "test.jpg", 10L);
        AttachFiles imageFiles = new AttachFiles(Collections.singletonList(imageFile));

        Spot spot = new Spot(spotId, member, null, false, title, null, imageFiles, null, rate);

        MemberInfo.SpotInfo spotInfo = new MemberInfo.SpotInfo(spot);
        List<MemberInfo.SpotInfo> spotList = Collections.singletonList(spotInfo);

        MemberInfo.SpotListResponse mockSpotListResponse = new MemberInfo.SpotListResponse(
            spotList,
            totalPages
        );

        when(memberService.retrieveSpotList(anyLong(), any(Pageable.class), anyString())).thenReturn(
            mockSpotListResponse);

        // When
        var result = memberFacade.getMemberSpotInfo(memberId, pageable, "private");

        // Then
        assertThat(result).isNotNull()
            .isInstanceOf(MemberInfo.SpotListResponse.class)
            .usingRecursiveComparison().isEqualTo(mockSpotListResponse);
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

    @DisplayName("getFollowerList 메서드가 유효한 요청이 들어왔을 때 MemberService의 getFollowerList 메서드를 잘 호출하는지")
    @Test
    void WhenGetFollowerList_ThenShouldReturnFollowerResponse() {
        Long memberId = 1L;
        PageRequest pageable = mock(PageRequest.class);

        MemberInfo.FollowerResponse mockFollowerResponse = new MemberInfo.FollowerResponse(
            Collections.emptyList(), false);
        when(memberService.getFollowerList(memberId, pageable)).thenReturn(mockFollowerResponse);

        var result = memberFacade.getFollowerList(memberId, pageable);

        assertThat(result).isNotNull()
            .isInstanceOf(MemberInfo.FollowerResponse.class)
            .usingRecursiveComparison().isEqualTo(mockFollowerResponse);
    }

    @DisplayName("getFollowerList 메서드가 유효한 요청이 들어왔을 때 MemberService의 getFollowerList 메서드를 잘 호출하는지")
    @Test
    void WhenGetFollowingList_ThenShouldReturnFollowingResponse() {
        Long memberId = 1L;
        PageRequest pageable = mock(PageRequest.class);

        MemberInfo.FollowingResponse mockFollowingResponse = new MemberInfo.FollowingResponse(
            Collections.emptyList(), false);
        when(memberService.getFollowingList(anyLong(), any(Pageable.class))).thenReturn(mockFollowingResponse);

        var result = memberFacade.getFollowingList(memberId, pageable);

        assertThat(result).isNotNull()
            .isInstanceOf(MemberInfo.FollowingResponse.class)
            .usingRecursiveComparison().isEqualTo(mockFollowingResponse);
    }

    @DisplayName("setTravelsVisibility 메서드가 유효한 요청이 들어왔을 때 MemberService의 setTravelsVisibility 메서드를 잘 호출하는지")
    @Test
    void WhenSetTravelsVisibility_ThenShouldReturnVisibilityChangeResponse() {
        Long memberId = 1L;
        VisibilityChangeRequest command = mock(VisibilityChangeRequest.class);

        MemberInfo.VisibilityChangeResponse mockVisibilityChangeResponse = new MemberInfo.VisibilityChangeResponse(
            "공개여부가 변경되었습니다.");
        when(memberService.setTravelsVisibility(memberId, command)).thenReturn(mockVisibilityChangeResponse);

        var result = memberFacade.setTravelsVisibility(memberId, command);

        assertThat(result).isNotNull()
            .isInstanceOf(MemberInfo.VisibilityChangeResponse.class)
            .usingRecursiveComparison().isEqualTo(mockVisibilityChangeResponse);
    }
}