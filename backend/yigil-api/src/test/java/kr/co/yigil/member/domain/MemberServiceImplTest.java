package kr.co.yigil.member.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.FollowReader;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberCommand.MemberUpdateRequest;
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
            mockFile, List.of(1L, 2L, 3L));

        Member mockMember = mock(Member.class);

        AttachFile mockAttachFile = mock(AttachFile.class);

        when(memberReader.getMember(anyLong())).thenReturn(mockMember);
        when(fileUploader.upload(mockFile)).thenReturn(mockAttachFile);

        when(mockAttachFile.getFileUrl()).thenReturn("images/image.jpg");

        memberService.updateMemberInfo(memberId, request);
        verify(mockMember).updateMemberInfo(anyString(), anyString(), anyString(), anyString(), anyList());
    }
}