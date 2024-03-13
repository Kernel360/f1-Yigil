package kr.co.yigil.member.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

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

import kr.co.yigil.member.application.MemberFacade;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.interfaces.dto.MemberDto;
import kr.co.yigil.member.interfaces.dto.MemberDto.Main;
import kr.co.yigil.member.interfaces.dto.mapper.MemberDtoMapper;

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
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }

    @DisplayName("내 정보 조회가 잘 되는지")
    @Test
    void getMyInfo_ShouldReturnOk() throws Exception {

        MemberDto.FavoriteRegion favoriteRegion1 = MemberDto.FavoriteRegion.builder()
            .id(1L)
            .name("서울")
            .build();
        MemberDto.FavoriteRegion favoriteRegion2 = MemberDto.FavoriteRegion.builder()
            .id(2L)
            .name("경기")
            .build();

        MemberDto.Main response = Main.builder()
            .memberId(1L)
            .email("test@yigil.co.kr")
            .nickname("test user")
            .profileImageUrl("https://cdn.igil.co.kr/images/profile.jpg")
            .ages("10대")
            .gender("남성")
            .favoriteRegions(List.of(favoriteRegion1, favoriteRegion2))
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
                    fieldWithPath("ages").description("연령대"),
                    fieldWithPath("gender").description("성별"),
                    fieldWithPath("favorite_regions").description("좋아하는 지역리스트"),
                    fieldWithPath("favorite_regions[].id").description("좋아하는 지역 ID"),
                    fieldWithPath("favorite_regions[].name").description("좋아하는 지역 이름"),
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
        String requestJson = "{\n"
            + "  \"nickname\": \"nickname\",\n"
            + "  \"ages\": \"10대\",\n"
            + "  \"gender\": \"남성\",\n"
            + "  \"favoriteRegionIds\": [1, 2, 3]\n"
            + "}";

        mockMvc.perform(multipart("/api/v1/members")
                .file("profileImageFile", multipartFile.getBytes())
                .content(requestJson)
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
                requestFields(
                    fieldWithPath("nickname").description("닉네임"),
                    fieldWithPath("ages").description("연령대"),
                    fieldWithPath("gender").description("성별"),
                    fieldWithPath("favoriteRegionIds").description("좋아하는 지역 ID 리스트")
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
        MemberDto.FavoriteRegion favoriteRegion1 = MemberDto.FavoriteRegion.builder()
            .id(1L)
            .name("서울")
            .build();
        MemberDto.FavoriteRegion favoriteRegion2 = MemberDto.FavoriteRegion.builder()
            .id(2L)
            .name("경기")
            .build();

        Long memberId = 1L;
        MemberDto.Main response = Main.builder()
            .memberId(1L)
            .email("test@yigil.co.kr")
            .nickname("test user")
            .profileImageUrl("https://cdn.yigil.co.kr/images/profile.jpg")
            .favoriteRegions(List.of(favoriteRegion1, favoriteRegion2))
            .ages("10대")
            .gender("남성")
            .followerCount(10)
            .followingCount(20)
            .build();

        when(memberFacade.getMemberInfo(anyLong())).thenReturn(mock(MemberInfo.Main.class));
        when(memberDtoMapper.of(any(MemberInfo.Main.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/members/{memberId}", memberId))
            .andExpect(status().isOk())
            .andDo(document(
                "members/get-member-info",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("memberId").description("회원 ID")
                ),
                responseFields(
                    fieldWithPath("member_id").description("회원 ID"),
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("nickname").description("닉네임"),
                    fieldWithPath("profile_image_url").description("프로필 이미지 URL"),
                    fieldWithPath("ages").description("연령대"),
                    fieldWithPath("gender").description("성별"),
                    fieldWithPath("favorite_regions").description("좋아하는 지역리스트"),
                    fieldWithPath("favorite_regions[].id").description("좋아하는 지역 ID"),
                    fieldWithPath("favorite_regions[].name").description("좋아하는 지역 이름"),
                    fieldWithPath("following_count").description("팔로잉 수"),
                    fieldWithPath("follower_count").description("팔로워 수")
                )
            ));

        verify(memberFacade).getMemberInfo(anyLong());
    }

    @DisplayName("닉네임 중복 체크가 잘 되는지")
    @Test
    void whenNicknameDuplicateCheck_thenShouldReturn200AndResponse() throws Exception {

        MemberInfo.NicknameCheckInfo checkInfo = new MemberInfo.NicknameCheckInfo(true);
        MemberDto.NicknameCheckResponse response = MemberDto.NicknameCheckResponse.builder()
            .available(true)
            .build();

        when(memberFacade.nicknameDuplicateCheck(anyString())).thenReturn(checkInfo);
        when(memberDtoMapper.of(any(MemberInfo.NicknameCheckInfo.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/members/nickname_duplicate_check")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\": \"nickname\"}"))
            .andExpect(status().isOk())
            .andDo(document(
                "members/nickname-duplicate-check",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("nickname").description("닉네임")
                ),
                responseFields(
                    fieldWithPath("available").description("사용 가능 여부")
                )
            ));

        verify(memberFacade).nicknameDuplicateCheck(anyString());
    }
}