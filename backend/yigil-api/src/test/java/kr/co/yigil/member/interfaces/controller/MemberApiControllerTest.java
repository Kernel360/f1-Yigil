package kr.co.yigil.member.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.member.application.MemberFacade;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.interfaces.dto.MemberDto;
import kr.co.yigil.member.interfaces.dto.MemberDto.Main;
import kr.co.yigil.member.interfaces.dto.mapper.MemberDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(MemberApiController.class)
@EnableSpringDataWebSupport
class MemberApiControllerTest {

    @MockBean
    private MemberFacade memberFacade;

    @MockBean
    private MemberDtoMapper memberDtoMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation)
                .uris()
                .withScheme("https")
                .withHost("yigil.co.kr")
                .withPort(80)
                )
            .build();
    }

    @DisplayName("내 정보 조회가 잘 되는지")
    @Test
    void getMyInfo_ShouldReturnOk() throws Exception {

        MemberDto.Main response = Main.builder()
            .memberId(1L)
            .email("test@yigil.co.kr")
            .nickname("test user")
            .profileImageUrl("https://cdn.igil.co.kr/images/profile.jpg")
            .followerCount(10)
            .followingCount(20)
            .build();

        when(memberFacade.getMemberInfo(anyLong())).thenReturn(mock(MemberInfo.Main.class));
        when(memberDtoMapper.of(any(MemberInfo.Main.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/members"))
            .andExpect(status().isOk())
            .andDo(document(
                "members/get-my-info",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("member_id").description("회원 ID"),
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("nickname").description("닉네임"),
                    fieldWithPath("profile_image_url").description("프로필 이미지 URL"),
                    fieldWithPath("following_count").description("팔로잉 수"),
                    fieldWithPath("follower_count").description("팔로워 수")
                )
            ));

        verify(memberFacade).getMemberInfo(anyLong());
    }



    @DisplayName("내 정보 업데이트가 잘 되는지")
    @Test
    void updateMyInfo_ShouldReturnOk() throws Exception {

        MultipartFile multipartFile = new MockMultipartFile(
            "profileImageFile",          // 필드 이름
            "hello.txt",                  // 원본 파일 이름
            "image/jpeg",   // 컨텐츠 타입
            "Hello, World!".getBytes()    // 파일 내용
        );

        when(memberFacade.updateMemberInfo(anyLong(), any())).thenReturn(
            mock(MemberInfo.MemberUpdateResponse.class));
        when(memberDtoMapper.of(any(MemberInfo.MemberUpdateResponse.class))).thenReturn(
            MemberDto.MemberUpdateResponse.builder()
                .message("회원 정보 업데이트 성공")
                .build()
        );

        mockMvc.perform(multipart("/api/v1/members")
                .file("profileImageFile", multipartFile.getBytes())
                .param("nickname", "nickname")
                .param("age", "10대")
                .param("gender", "남성")
                .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andExpect(status().isOk())
            .andDo(document(
                "members/update-my-info",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParts(
                    partWithName("profileImageFile").description("프로필 이미지 파일")
                ),
                responseFields(
                    fieldWithPath("message").description("메시지")
                )
            ));
    }

    @DisplayName("회원 탈퇴가 잘 되는지")
    @Test
    void WhenWithdraw_ThenShouldReturnOk() throws Exception {
        MemberDto.MemberDeleteResponse response = MemberDto.MemberDeleteResponse.builder()
            .message("회원 탈퇴 성공")
            .build();

        when(memberFacade.withdraw(anyLong())).thenReturn(
            mock(MemberInfo.MemberDeleteResponse.class));
        when(memberDtoMapper.of(any(MemberInfo.MemberDeleteResponse.class))).thenReturn(response);

        mockMvc.perform(delete("/api/v1/members"))
            .andExpect(status().isOk())
            .andDo(document(
                "members/withdraw",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("message").description("메시지")
                )
            ));

        verify(memberFacade).withdraw(anyLong());
    }

    @DisplayName("회원 정보 조회가 잘 되는지")
    @Test
    void WhenGetMemberInfo_ThenShouldReturnOk() throws Exception {
        MemberDto.Main response = Main.builder()
            .memberId(1L)
            .email("test@yigil.co.kr")
            .nickname("test user")
            .profileImageUrl("https://cdn.yigil.co.kr/images/profile.jpg")
            .followerCount(10)
            .followingCount(20)
            .build();

        when(memberFacade.getMemberInfo(anyLong())).thenReturn(mock(MemberInfo.Main.class));
        when(memberDtoMapper.of(any(MemberInfo.Main.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/members/{memberId}", 1L))
            .andExpect(status().isOk())
            .andDo(document(
                "members/get-member-info",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("member_id").description("회원 ID"),
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("nickname").description("닉네임"),
                    fieldWithPath("profile_image_url").description("프로필 이미지 URL"),
                    fieldWithPath("following_count").description("팔로잉 수"),
                    fieldWithPath("follower_count").description("팔로워 수")
                )
            ));

        verify(memberFacade).getMemberInfo(anyLong());
    }
}