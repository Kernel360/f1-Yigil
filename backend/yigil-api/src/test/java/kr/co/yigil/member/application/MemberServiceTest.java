//package kr.co.yigil.member.application;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import kr.co.yigil.file.FileUploadEvent;
//import kr.co.yigil.follow.application.FollowRedisIntegrityService;
//import kr.co.yigil.follow.domain.Follow;
//import kr.co.yigil.follow.domain.FollowCount;
//import kr.co.yigil.follow.domain.repository.FollowRepository;
//import kr.co.yigil.global.exception.BadRequestException;
//import kr.co.yigil.member.Member;
//import kr.co.yigil.member.SocialLoginType;
//import kr.co.yigil.member.repository.MemberRepository;
//import kr.co.yigil.member.dto.request.MemberUpdateRequest;
//import kr.co.yigil.member.dto.response.MemberDeleteResponse;
//import kr.co.yigil.member.dto.response.MemberFollowerListResponse;
//import kr.co.yigil.member.dto.response.MemberFollowingListResponse;
//import kr.co.yigil.member.dto.response.MemberInfoResponse;
//import kr.co.yigil.member.dto.response.MemberUpdateResponse;
//import kr.co.yigil.post.domain.Post;
//import kr.co.yigil.post.domain.repository.PostRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.mock.web.MockMultipartFile;
//
//public class MemberServiceTest {
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @Mock
//    private PostRepository postRepository;
//
//    @InjectMocks
//    private MemberService memberService;
//
//    @Mock
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Mock
//    private FollowRepository followRepository;
//
//    @Mock
//    private FollowRedisIntegrityService followRedisIntegrityService;
//
//    @Mock
//    private ApplicationEventPublisher applicationEventPublisher;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @DisplayName("getMemberInfo 메서드에 유효한 사용자ID가 주어졌을 때 사용자 정보가 잘 반환되는지")
//    @Test
//    void whenGetMemberInfo_thenReturnsMemberInfoResponse_withValidMemberInfo() {
//        Long memberId = 1L;
//        Member mockMember = new Member("kiit0901@gmail.com", "123456", "stone", "profile.jpg", "kakao");
//        List<Post> mockPostList = new ArrayList<>();
//        FollowCount mockFollowCount = new FollowCount(1L, 0, 0);
//
//        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));
//        when(postRepository.findAllByMember(mockMember)).thenReturn(mockPostList);
//        when(followRedisIntegrityService.ensureFollowCounts(mockMember)).thenReturn(mockFollowCount);
//
//        doAnswer(invocation -> {
//            FileUploadEvent event = invocation.getArgument(0);
//            event.getCallback().accept("mockUrl");
//            return null;
//        }).when(applicationEventPublisher).publishEvent(any(FileUploadEvent.class));
//
//        MemberInfoResponse response = memberService.getMemberInfo(memberId);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getNickname()).isEqualTo("stone");
//        assertThat(response.getProfileImageUrl()).isEqualTo("profile.jpg");
//    }
//
//    @DisplayName("getMemberInfo 메서드에 잘못된 사용자ID가 주어졌을 때 예외가 잘 발생하는지")
//    @Test
//    void whenGetMemberInfo_thenThrowsException_withInvalidMemberInfo() {
//        Long invalidMemberId = 2L;
//
//        when(memberRepository.findById(invalidMemberId)).thenReturn(Optional.empty());
//
//        assertThrows(BadRequestException.class, () -> memberService.getMemberInfo(invalidMemberId));
//    }
//
//    @DisplayName("updateMemberInfo 메서드가 유효한 사용자 Id가 주어졌을 때 이벤트가 잘 발행되는지")
//    @Test
//    void whenUpdateMemberInfo_thenReturnsUpdateInfoAndPublishEvent_withValidMemberInfo() {
//        Long validMemberId = 1L;
//        MemberUpdateRequest request = mock(MemberUpdateRequest.class);
//        MockMultipartFile file = new MockMultipartFile("file", "filename.jpg", "image/jpeg", new byte[10]);
//        when(request.getProfileImageFile()).thenReturn(file);
//
//        Member mockMember = new Member(1L, "kiit0901@gmail.com", "123456", "stone", "profile.jpg", SocialLoginType.KAKAO);
//        when(memberRepository.findById(validMemberId)).thenReturn(Optional.of(mockMember));
//        when(memberRepository.save(mockMember)).thenReturn(mockMember);
//
//        doAnswer(invocation -> {
//            FileUploadEvent event = invocation.getArgument(0);
//            event.getCallback().accept("mockUrl");
//            return null;
//        }).when(applicationEventPublisher).publishEvent(any(FileUploadEvent.class));
//
//        MemberUpdateResponse response = memberService.updateMemberInfo(validMemberId, request);
//
//        verify(applicationEventPublisher).publishEvent(any(FileUploadEvent.class));
//        assertEquals("회원 정보 업데이트 성공", response.getMessage());
//    }
//
//    @DisplayName("updateMemberInfo 메서드가 유효하지 않은 사용자 Id가 주어졌을 때 예외가 잘 발생하는지")
//    @Test
//    void whenUpdateMemberInfo_thenThrowsException_withInvalidMemberInfo() {
//        Long invalidMemberId = 1L;
//        MemberUpdateRequest request = new MemberUpdateRequest();
//        when(memberRepository.findById(invalidMemberId)).thenReturn(Optional.empty());
//
//        assertThrows(BadRequestException.class, () -> memberService.updateMemberInfo(invalidMemberId, request));
//    }
//
//    @DisplayName("withdraw 메서드가 유효한 사용자 ID가 주어졌을 때 회원 탈퇴가 잘 동작하는지")
//    @Test
//    void whenWithdraw_shouldDeleteMember_withValidMemberInfo() {
//        Long validMemberId = 1L;
//        Member mockMember = new Member(1L, "kiit0901@gmail.com", "123456", "stone", "profile.jpg", SocialLoginType.KAKAO);
//        when(memberRepository.findById(validMemberId)).thenReturn(Optional.of(mockMember));
//
//        MemberDeleteResponse response = memberService.withdraw(validMemberId);
//
//        verify(memberRepository).delete(mockMember);
//        assertEquals("회원 탈퇴 성공",  response.getMessage());
//    }
//
//    @DisplayName("withdraw 메서드가 유효하지 않은 사용자 ID가 주어졌을 때 예외를 잘 발생시키는지")
//    @Test
//    void whenWithdraw_shouldThrowException_withInvalidMemberInfo() {
//        Long invalidMemberId = 1L;
//        when(memberRepository.findById(invalidMemberId)).thenReturn(Optional.empty());
//
//        assertThrows(BadRequestException.class, () -> memberService.withdraw(invalidMemberId));
//    }
//
//    @DisplayName("getFollowerList 메서드가 유효한 사용자 ID가 주어졌을 때 팔로워 목록을 잘 반환하는지")
//    @Test
//    void whenGetFollowerList_shouldReturnFollowerList_withValidMemberInfo() {
//        Long validMemberId = 1L;
//        Member mockMember = new Member(1L, "kiit0901@gmail.com", "123456", "stone", "profile.jpg", SocialLoginType.KAKAO);
//        List<Follow> mockFollows = new ArrayList<>();
//
//        Member follower1 = new Member(2L, "follower1@gmail.com", "123456", "follower1", "profile1.jpg", SocialLoginType.KAKAO);
//        Member follower2 = new Member(3L, "follower2@gmail.com", "123456", "follower2", "profile2.jpg", SocialLoginType.KAKAO);
//        mockFollows.add(new Follow(follower1, mockMember));
//        mockFollows.add(new Follow(follower2, mockMember));
//
//        when(memberRepository.findById(validMemberId)).thenReturn(Optional.of(mockMember));
//        when(followRepository.findAllByFollowing(mockMember)).thenReturn(mockFollows);
//
//        MemberFollowerListResponse response = memberService.getFollowerList(validMemberId);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getFollowerList()).hasSize(2);
//        assertThat(response.getFollowerList()).extracting("nickname").contains("follower1", "follower2");
//    }
//
//    @DisplayName("getFollowingList 메서드가 유효한 사용자 ID가 주어졌을 때 팔로잉 목록을 잘 반혼하는지")
//    @Test
//    void whenGetFollowingList_shouldReturnFollowingList_withValidMemberInfo() {
//        Long validMemberId = 1L;
//        Member mockMember = new Member(1L, "kiit0901@gmail.com", "123456", "stone", "profile.jpg", SocialLoginType.KAKAO);
//        List<Follow> mockFollows = new ArrayList<>();
//
//        Member following1 = new Member(2L, "following1@gmail.com", "123456", "following1", "profile1.jpg", SocialLoginType.KAKAO);
//        Member following2 = new Member(3L, "following2@gmail.com", "123456", "following2", "profile2.jpg", SocialLoginType.KAKAO);
//        mockFollows.add(new Follow(mockMember, following1));
//        mockFollows.add(new Follow(mockMember, following2));
//
//        when(memberRepository.findById(validMemberId)).thenReturn(Optional.of(mockMember));
//        when(followRepository.findAllByFollower(mockMember)).thenReturn(mockFollows);
//
//        MemberFollowingListResponse response = memberService.getFollowingList(validMemberId);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getFollowingList()).hasSize(2);
//        assertThat(response.getFollowingList()).extracting("nickname").contains("following1", "following2");
//    }
//}
