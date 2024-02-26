package kr.co.yigil.member.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileType;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.FollowReader;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberCommand.MemberUpdateRequest;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.TravelReader;
import kr.co.yigil.travel.domain.course.CourseReader;
import kr.co.yigil.travel.domain.spot.SpotReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberReader memberReader;
    @Mock
    private MemberStore memberStore;
    @Mock
    private CourseReader courseReader;
    @Mock
    private TravelReader travelReader;
    @Mock
    private SpotReader spotReader;
    @Mock
    private FollowReader followReader;

    @DisplayName("retrieveMemberInfo 를 호출했을 때 멤버 정보 조회가 잘 되는지 확인")
    @Test
    void WhenRetrieveMemberInfo_ThenReturnMemberInfoMain() {
        Long memberId = 1L;
        Member mockMember = mock(Member.class);
        FollowCount followCount = mock(FollowCount.class);

        when(memberReader.getMember(anyLong())).thenReturn(mockMember);
        when(followReader.getFollowCount(anyLong())).thenReturn(followCount);

        var result = memberService.retrieveMemberInfo(memberId);

        assertThat(result).isNotNull().isInstanceOf(MemberInfo.Main.class);
    }

    @DisplayName("withdrawal 를 호출했을 때 회원 탈퇴가 잘 되는지 확인")
    @Test
    void WhenWithdrawal_ThenShouldNotOccuredError() {
        Long memberId = 1L;

        memberService.withdrawal(memberId);
        verify(memberStore).deleteMember(memberId);
    }

    @DisplayName("updateMemberInfo 를 호출했을 때 회원 정보 업데이트가 잘 되는지 확인")
    @Test
    void WhenUpdateMemberInfo_ThenShouldNotOccuredError() {
        Long memberId = 1L;
        MultipartFile mockFile = new MockMultipartFile("file", "UploadOriginalFileName.jpg",
            "image/jpeg",
            "test".getBytes());
        MemberCommand.MemberUpdateRequest request = new MemberUpdateRequest("nickname", "10대", "여성",
            mockFile);

        String currentProfileImageUrl = "current.jpg";
        Member mockMember = new Member(memberId, null, null, null, currentProfileImageUrl, null,
            null, null);

        when(memberReader.getMember(anyLong())).thenReturn(mockMember);

        var result = memberService.updateMemberInfo(memberId, request);
        assertThat(result).isTrue();
    }

    @DisplayName("retrieveSpotList 를 호출했을 때 스팟 리스트 조회가 잘 되는지 확인")
    @Test
    void WhenRetrieveSpotList_ThenShouldReturnSpotListResponse() {
        Long memberId = 1L;
        Long spotId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);
        String selected = "private";
        AttachFile mockAttachFile = new AttachFile(mock(FileType.class), "fileUrl",
            "originalFileName", 100L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile));
        Member mockMember = mock(Member.class);
        Spot mockSpot = new Spot(spotId, mockMember, mock(Point.class), false, "title",
            "description", mockAttachFiles, null, 3.5);
        PageImpl<Spot> mockSpotList = new PageImpl<>(List.of(mockSpot));
        when(spotReader.getMemberSpotList(anyLong(), any(), any())).thenReturn(mockSpotList);

        var result = memberService.retrieveSpotList(memberId, pageable, selected);

        assertThat(result).isNotNull().isInstanceOf(MemberInfo.SpotListResponse.class);
        assertThat(result.getContent().getFirst()).isInstanceOf(MemberInfo.SpotInfo.class);
    }

    @DisplayName("retrieveCourseList 를 호출했을 때 코스 리스트 조회가 잘 되는지 확인")
    @Test
    void WhenRetrieveCourseList_ThenShouldReturnCourseListResponse() {
        Long memberId = 1L;
        Long courseId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);
        String selected = "private";
        // 필요 course 필드: id, title, rate, spotList, mapstaticImageUrl

        Course mockCourse = new Course(
            courseId, mock(Member.class), "title", null, 4.5, mock(LineString.class), false,
            List.of(mock(Spot.class)), 1, mock(AttachFile.class));
        PageImpl<Course> mockCourseList = new PageImpl<>(List.of(mockCourse));


        when(courseReader.getMemberCourseList(anyLong(), any(), any())).thenReturn(mockCourseList);

        var result = memberService.retrieveCourseList(memberId, pageable, selected);

        assertThat(result).isNotNull().isInstanceOf(MemberInfo.CourseListResponse.class);
        assertThat(result.getContent().getFirst()).isInstanceOf(MemberInfo.CourseInfo.class);
    }

    @DisplayName("getFollowerList 를 호출했을 때 팔로워 리스트 조회가 잘 되는지 확인")
    @Test
    void WhenGetFollowerList_ThenShouldReturnFollowerResponse() {
        Long memberId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        // 필수 멤버 필드:   id, nickname, profileImageUrl
        Member mockMember = new Member(memberId, null, null, "nickname", "profileImageUrl", null);
        Follow mockFollow = mock(Follow.class);

        Slice<Follow> mockFollowList = new SliceImpl<>(List.of(mockFollow));

        when(followReader.getFollowerSlice(anyLong(), any())).thenReturn(mockFollowList);
        when(mockFollow.getFollower()).thenReturn(mockMember);

        var result = memberService.getFollowerList(memberId, pageable);

        assertThat(result).isNotNull().isInstanceOf(MemberInfo.FollowerResponse.class);
    }

    @DisplayName("getFollowingList 를 호출했을 때 팔로잉 리스트 조회가 잘 되는지 확인")
    @Test
    void WhenGetFollowingList_ThenShouldReturnFollowingResponse() {
        Long memberId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        // 필수 멤버 필드:   id, nickname, profileImageUrl
        Member mockMember = new Member(memberId, null, null, "nickname", "profileImageUrl", null);
        Follow mockFollow = mock(Follow.class);

        Slice<Follow> mockFollowList = new SliceImpl<>(List.of(mockFollow));

        when(followReader.getFollowingSlice(anyLong(), any())).thenReturn(mockFollowList);
        when(mockFollow.getFollowing()).thenReturn(mockMember);

        var result = memberService.getFollowingList(memberId, pageable);

        assertThat(result).isNotNull().isInstanceOf(MemberInfo.FollowingResponse.class);
    }

    @DisplayName("setTravelsVisibility 를 호출했을 때 여행 리스트의 공개 여부가 잘 변경되는지 확인")
    @Test
    void WhenSetTravelsVisibility_ThenReturnVisibilityChangeResponse() {
        Long memberId = 1L;
        Long travelId1 = 1L;
        Long travelId2 = 2L;

        MemberCommand.VisibilityChangeRequest command = new MemberCommand.VisibilityChangeRequest(
            List.of(travelId1, travelId2), true);

        var result = memberService.setTravelsVisibility(memberId, command);

        verify(travelReader).setTravelsVisibility(anyLong(), anyList(), any(Boolean.class));
        assertThat(result).isNotNull().isInstanceOf(MemberInfo.VisibilityChangeResponse.class);
    }
}