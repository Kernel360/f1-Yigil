package kr.co.yigil.member.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.FollowReader;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberCommand.MemberUpdateRequest;
import kr.co.yigil.region.domain.Region;
import kr.co.yigil.region.domain.RegionReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    private FollowReader followReader;
    @Mock
    private FileUploader fileUploader;

    @Mock
    private RegionReader regionReader;

    @DisplayName("retrieveMemberInfo 를 호출했을 때 멤버 정보 조회가 잘 되는지 확인")
    @Test
    void WhenRetrieveMemberInfo_ThenReturnMemberInfoMain() {
        Long memberId = 1L;
        Member mockMember = mock(Member.class);
        FollowCount followCount = mock(FollowCount.class);

        when(memberReader.getMember(anyLong())).thenReturn(mockMember);

        when(followReader.getFollowCount(anyLong())).thenReturn(followCount);
        when(mockMember.getAges()).thenReturn(Ages.TEENAGERS);
        when(mockMember.getGender()).thenReturn(Gender.MALE);

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
        Member mockMember = new Member(memberId, "originalEmail", "socialLoginId", "originalNickname",
            "profileImageUrl", SocialLoginType.KAKAO, Ages.TEENAGERS, Gender.FEMALE);
        MemberUpdateRequest request = new MemberUpdateRequest("updatedNickname", "20", "male",
            mockFile, List.of(1L, 2L, 3L), false);

        AttachFile mockAttachFile = mock(AttachFile.class);

        when(memberReader.getMember(anyLong())).thenReturn(mockMember);
        when(fileUploader.upload(mockFile)).thenReturn(mockAttachFile);

        memberService.updateMemberInfo(memberId, request);

        assertThat(mockMember.getNickname()).isEqualTo(request.getNickname());
        assertThat(mockMember.getAges()).isEqualTo(Ages.TWENTIES);
        assertThat(mockMember.getGender()).isEqualTo(Gender.MALE);
        assertThat(mockMember.getProfileImageUrl()).isEqualTo(mockAttachFile.getFileUrl());
    }
    @DisplayName("isProfileEmpty가 null일 때 회원 정보 업데이트가 잘 되는지 확인")
    @Test
    void WhenUpdateMemberInfoAndIsProfileEmptyIsNull_ThenShouldNotOccuredError() {
        Long memberId = 1L;
        MultipartFile mockFile = new MockMultipartFile("file", "UploadOriginalFileName.jpg",
            "image/jpeg",
            "test".getBytes());
        Member mockMember = new Member(memberId, "originalEmail", "socialLoginId", "originalNickname",
            "profileImageUrl", SocialLoginType.KAKAO, Ages.TEENAGERS, Gender.FEMALE);
        MemberUpdateRequest request = new MemberUpdateRequest("updatedNickname", "20", "male",
            mockFile, List.of(1L, 2L, 3L), null);

        AttachFile mockAttachFile = mock(AttachFile.class);

        when(memberReader.getMember(anyLong())).thenReturn(mockMember);
        when(fileUploader.upload(mockFile)).thenReturn(mockAttachFile);

        memberService.updateMemberInfo(memberId, request);

        assertThat(mockMember.getNickname()).isEqualTo(request.getNickname());
        assertThat(mockMember.getAges()).isEqualTo(Ages.TWENTIES);
        assertThat(mockMember.getGender()).isEqualTo(Gender.MALE);
        assertThat(mockMember.getProfileImageUrl()).isEqualTo(mockAttachFile.getFileUrl());
    }

    @DisplayName("imageFile 이 null이고 isProfileEmpty가 false일 때 멤버의 프로필 이미지가 그대로인지 확인")
    @Test
    void GivenImageFileIsNullAndIsProfileEmptyIsFalse_WhenMemberUpdate_ThenShouldNotUpateProfileImage() {
        Long memberId = 1L;
        Member member = new Member(memberId, "originalEmail", "socialLoginId", "originalNickname",
            "profileImageUrl", SocialLoginType.KAKAO, Ages.TEENAGERS, Gender.FEMALE);
        MemberUpdateRequest request = new MemberUpdateRequest("updatedNickname", "20", "male",
            null, List.of(1L, 2L, 3L), false);

        when(memberReader.getMember(memberId)).thenReturn(member);
        when(regionReader.getRegions(request.getFavoriteRegionIds())).thenReturn(List.of(mock(
            Region.class)));

        memberService.updateMemberInfo(memberId, request);

        assertThat(member.getProfileImageUrl()).isEqualTo("profileImageUrl");
        assertThat(member.getNickname()).isEqualTo("updatedNickname");
        assertThat(member.getAges()).isEqualTo(Ages.TWENTIES);
        assertThat(member.getGender()).isEqualTo(Gender.MALE);
    }

    @DisplayName("imageFile 이 null이고 isProfileEmpty가 null일 때 멤버의 프로필 이미지가 그대로인지 확인")
    @Test
    void GivenImageFileIsNullAndIsProfileEmptyIsNULL_WhenMemberUpdate_ThenShouldNotUpateProfileImage() {
        Long memberId = 1L;
        Member member = new Member(memberId, "originalEmail", "socialLoginId", "originalNickname",
            "profileImageUrl", SocialLoginType.KAKAO, Ages.TEENAGERS, Gender.FEMALE);
        MemberUpdateRequest request = new MemberUpdateRequest("updatedNickname", "20", "male",
            null, List.of(1L, 2L, 3L), null);

        when(memberReader.getMember(memberId)).thenReturn(member);
        when(regionReader.getRegions(request.getFavoriteRegionIds())).thenReturn(List.of(mock(
            Region.class)));

        memberService.updateMemberInfo(memberId, request);

        assertThat(member.getProfileImageUrl()).isEqualTo("profileImageUrl");
        assertThat(member.getNickname()).isEqualTo("updatedNickname");
        assertThat(member.getAges()).isEqualTo(Ages.TWENTIES);
        assertThat(member.getGender()).isEqualTo(Gender.MALE);
    }

    @DisplayName("imageFile 이 null이고 isEmptyProfile이 true일 때 member의 profileImageUrl 이 빈스트링으로 업데이트 되는지 확인")
    @Test
    void GivenImageFileIsNullAndIsProfileEmptyIsTrue_WhenMemberUpdate_ThenProfileImageShouldEmptyString() {
        Long memberId = 1L;

        Member member = new Member(memberId, "originalEmail", "socialLoginId", "originalNickname",
            "profileImageUrl", SocialLoginType.KAKAO, Ages.TEENAGERS, Gender.FEMALE);
        MemberUpdateRequest request = new MemberUpdateRequest(null, null, null,
            null, null, true);
        when(memberReader.getMember(memberId)).thenReturn(member);

        memberService.updateMemberInfo(memberId, request);

        assertThat(member.getProfileImageUrl()).isEmpty();
    }
    @DisplayName("imageFile 이 null이 아니고 isProfileEmpty가 false일 때 멤버의 프로필 이미지가 업데이트 되는지 확인")
    @Test
    void GivenImageFileIsNotNull_WhenMemberUpdate_ThenProfileImageShouldNotEmptyString() {
        Long memberId = 1L;
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg",
            "image/jpeg",
            "test".getBytes());

        Member member = new Member(memberId, "originalEmail", "socialLoginId", "originalNickname",
            "profileImageUrl", SocialLoginType.KAKAO, Ages.TEENAGERS, Gender.FEMALE);
        MemberUpdateRequest request = new MemberUpdateRequest(null, null, null,
            mockFile, null, false);
        when(memberReader.getMember(memberId)).thenReturn(member);
        when(fileUploader.upload(mockFile)).thenReturn(AttachFile.of("differentProfileImageUrl"));

        memberService.updateMemberInfo(memberId, request);

        assertThat(member.getProfileImageUrl()).isNotEqualTo("profileImageUrl");
    }

    @DisplayName("imageFile 이 null이 아니고 isProfileEmpty가 true일 때 에러를 발생하는 지 ")
    @Test
    void GivenImageFileIsNotNullAndIsProfileEmptyTrue_WhenMemberUpdate_ThenThrowAnError() {
        Long memberId = 1L;
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg",
            "image/jpeg",
            "test".getBytes());

        Member member = new Member(memberId, "originalEmail", "socialLoginId", "originalNickname",
            "profileImageUrl", SocialLoginType.KAKAO, Ages.TEENAGERS, Gender.FEMALE);
        MemberUpdateRequest request = new MemberUpdateRequest(null, null, null,
            mockFile, null, true);
        when(memberReader.getMember(memberId)).thenReturn(member);

        assertThatThrownBy(() -> memberService.updateMemberInfo(memberId, request))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining(ExceptionCode.INVALID_REQUEST.getMessage());
    }

    @DisplayName("nicknameDuplicateCheck 를 호출했을 때 닉네임 중복 체크가 잘 되는지 확인")
    @Test
    void whenNicknameDuplicateCheck() {
        String nickname = "nickname";
        when(memberReader.existsByNickname(nickname)).thenReturn(false);
        MemberInfo.NicknameCheckInfo result = memberService.nicknameDuplicateCheck(nickname);
        assertThat(result).isNotNull().isInstanceOf(MemberInfo.NicknameCheckInfo.class);
        assertThat(result.isAvailable()).isTrue();
    }
}