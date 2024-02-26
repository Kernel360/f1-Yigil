package kr.co.yigil.member.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
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
import org.springframework.data.domain.PageRequest;
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

    @DisplayName("내가 작성한 코스 목록 조회가 잘 되는지")
    @Test
    void getMyCourseInfo_ShouldReturnOk() throws Exception {

        MemberDto.CourseInfo courseInfo = MemberDto.CourseInfo.builder()
            .courseId(1L)
            .title("test course")
            .rate(4.5)
            .spotCount(10)
            .createdDate("2024-01-01")
            .mapStaticImageUrl("images/map.jpg")
            .isPrivate(false)
            .build();
        MemberDto.CourseListResponse response = MemberDto.CourseListResponse.builder()
            .content(List.of(courseInfo))
            .totalPages(1)
            .build();

        when(memberFacade.getMemberCourseInfo(anyLong(), any(PageRequest.class),
            anyString())).thenReturn(mock(MemberInfo.CourseListResponse.class));
        when(memberDtoMapper.of(any(MemberInfo.CourseListResponse.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/members/courses")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "createdAt")
                .param("sortOrder", "desc")
                .param("selected", "public")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "members/get-my-course-list",
                getDocumentRequest(),
                getDocumentResponse(),
                queryParameters(
                    parameterWithName("page").description("현재 페이지").optional(),
                    parameterWithName("size").description("페이지 크기").optional(),
                    parameterWithName("sortBy").description("정렬 옵션").optional(),
                    parameterWithName("sortOrder").description("정렬 순서").optional(),
                    parameterWithName("selected").description("필터 기능(전체 공개 비공개)").optional()
                ),
                responseFields(
                    fieldWithPath("content[].course_id").description("코스 ID"),
                    fieldWithPath("content[].title").description("코스 제목"),
                    fieldWithPath("content[].rate").description("코스 평점"),
                    fieldWithPath("content[].spot_count").description("코스 포함 장소 수"),
                    fieldWithPath("content[].created_date").description("코스 생성일"),
                    fieldWithPath("content[].map_static_image_url").description("코스 지도 이미지 URL"),
                    fieldWithPath("content[].is_private").description("공개여부"),
                    fieldWithPath("total_pages").description("총 페이지 수")
                )
            ));
        verify(memberFacade).getMemberCourseInfo(anyLong(), any(PageRequest.class), anyString());
    }

    @DisplayName("내가 작성한 장소 목록 조회가 잘 되는지")
    @Test
    void getMySpotInfo_ShouldReturnOk() throws Exception {

        MemberDto.SpotInfo spotInfo = MemberDto.SpotInfo.builder()
            .spotId(1L)
            .title("test course")
            .rate(4.5)
            .imageUrl("images/map.jpg")
            .createdDate("2024-01-01")
            .isPrivate(false)
            .build();
        MemberDto.SpotListResponse response = MemberDto.SpotListResponse.builder()
            .content(List.of(spotInfo))
            .totalPages(1)
            .build();

        when(memberFacade.getMemberSpotInfo(anyLong(), any(PageRequest.class),
            anyString())).thenReturn(mock(MemberInfo.SpotListResponse.class));
        when(memberDtoMapper.of(any(MemberInfo.SpotListResponse.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/members/spots")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "createdAt")
                .param("sortOrder", "desc")
                .param("selected", "public")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "members/get-my-spot-list",
                getDocumentRequest(),
                getDocumentResponse(),
                queryParameters(
                    parameterWithName("page").description("현재 페이지").optional(),
                    parameterWithName("size").description("페이지 크기").optional(),
                    parameterWithName("sortBy").description("정렬 옵션").optional(),
                    parameterWithName("sortOrder").description("정렬 순서").optional(),
                    parameterWithName("selected").description("필터 기능(전체 공개 비공개)").optional()
                ),
                responseFields(
                    fieldWithPath("content[].spot_id").description("장소 ID"),
                    fieldWithPath("content[].title").description("장소 제목"),
                    fieldWithPath("content[].rate").description("장소 평점"),
                    fieldWithPath("content[].image_url").description("장소 이미지 URL"),
                    fieldWithPath("content[].created_date").description("장소 생성일"),
                    fieldWithPath("content[].is_private").description("공개여부"),
                    fieldWithPath("total_pages").description("총 페이지 수")
                ))
            );
        verify(memberFacade).getMemberSpotInfo(anyLong(), any(PageRequest.class), anyString());
    }

    @DisplayName("스팟 및 코스의 공개 여부 설정이 잘 되는지")
    @Test
    void setTravelsVisibility_ShouldReturnOk() throws Exception {

        MemberDto.TravelsVisibilityResponse response = MemberDto.TravelsVisibilityResponse.builder()
            .message("")
            .build();

        when(memberFacade.setTravelsVisibility(anyLong(), any())).thenReturn(
            mock(MemberInfo.VisibilityChangeResponse.class));
        when(memberDtoMapper.of(any(MemberInfo.VisibilityChangeResponse.class))).thenReturn(
            response);

        mockMvc.perform(post("/api/v1/members/travels/visibility")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"visibility\": \"public\"}")
                .content("{\"travel_ids\": [1], \"is_private\": false}")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "members/set-travels-visibility",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("message").description("메시지")
                )
            ));
        verify(memberFacade).setTravelsVisibility(anyLong(), any());
    }

    @DisplayName("팔로우 목록 조회가 잘 되는지")
    @Test
    void WhenGetMyFollowerList_ThenShouldReturnOk() throws Exception {

        MemberDto.FollowerResponse response = MemberDto.FollowerResponse.builder()
            .content(List.of(MemberDto.FollowInfo.builder()
                .memberId(1L)
                .nickname("test user")
                .profileImageUrl("https://test.yigil.co.kr/profile.jpg")
                .build())
            )
            .hasNext(false)
            .build();

        when(memberFacade.getFollowerList(anyLong(), any(PageRequest.class))).thenReturn(
            mock(MemberInfo.FollowerResponse.class));
        when(memberDtoMapper.of(any(MemberInfo.FollowerResponse.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/members/followers")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "members/get-my-follower-list",
                getDocumentRequest(),
                getDocumentResponse(),
                queryParameters(
                    parameterWithName("page").description("현재 페이지").optional(),
                    parameterWithName("size").description("페이지 크기").optional(),
                    parameterWithName("sortBy").description("정렬 옵션").optional(),
                    parameterWithName("sortOrder").description("정렬 순서").optional()
                ),
                responseFields(
                    fieldWithPath("content[].member_id").description("회원 ID"),
                    fieldWithPath("content[].nickname").description("닉네임"),
                    fieldWithPath("content[].profile_image_url").description("프로필 이미지 URL"),
                    fieldWithPath("has_next").description("다음 페이지 존재 여부")
                )

            ));
        verify(memberFacade).getFollowerList(anyLong(), any(PageRequest.class));
    }

    @DisplayName("팔로잉 목록 조회가 잘 되는지")
    @Test
    void WhenGetMyFollowingList_ThenShouldReturnOk() throws Exception {

        MemberDto.FollowingResponse response = MemberDto.FollowingResponse.builder()
            .content(List.of(MemberDto.FollowInfo.builder()
                .memberId(1L)
                .nickname("test user")
                .profileImageUrl("https://test.yigil.co.kr/profile.jpg")
                .build())
            )
            .hasNext(false)
            .build();

        when(memberFacade.getFollowingList(anyLong(), any(PageRequest.class))).thenReturn(
            mock(MemberInfo.FollowingResponse.class));
        when(memberDtoMapper.of(any(MemberInfo.FollowingResponse.class))).thenReturn(response);
        mockMvc.perform(get("/api/v1/members/followings")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "members/get-my-following-list",
                getDocumentRequest(),
                getDocumentResponse(),
                queryParameters(
                    parameterWithName("page").description("현재 페이지").optional(),
                    parameterWithName("size").description("페이지 크기").optional(),
                    parameterWithName("sortBy").description("정렬 옵션").optional(),
                    parameterWithName("sortOrder").description("정렬 순서").optional()
                ),
                responseFields(
                    fieldWithPath("content[].member_id").description("회원 ID"),
                    fieldWithPath("content[].nickname").description("닉네임"),
                    fieldWithPath("content[].profile_image_url").description("프로필 이미지 URL"),
                    fieldWithPath("has_next").description("다음 페이지 존재 여부")
                )
            ));
        verify(memberFacade).getFollowingList(anyLong(), any(PageRequest.class));
    }

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

    @DisplayName("회원을 팔로우하는 목록이 잘 조회되는지")
    @Test
    void WhenGetMemberFollowerList_ThenReturnOk() throws Exception {

        MemberDto.FollowerResponse response = MemberDto.FollowerResponse.builder()
            .content(List.of(MemberDto.FollowInfo.builder()
                .memberId(1L)
                .nickname("test user")
                .profileImageUrl("https://cdn.yigil.co.kr/images/profile.jpg")
                .build())
            )
            .hasNext(false)
            .build();

        when(memberFacade.getFollowerList(anyLong(), any(PageRequest.class))).thenReturn(
            mock(MemberInfo.FollowerResponse.class));
        when(memberDtoMapper.of(any(MemberInfo.FollowerResponse.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/members/followers/{memberId}", 1L)
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "members/get-member-follower-list",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("content[].member_id").description("회원 ID"),
                    fieldWithPath("content[].nickname").description("닉네임"),
                    fieldWithPath("content[].profile_image_url").description("프로필 이미지 URL"),
                    fieldWithPath("has_next").description("다음 페이지 존재 여부")
                )
            ));

        verify(memberFacade).getFollowerList(anyLong(), any(PageRequest.class));

    }

    @DisplayName("회원이 팔로우하는 목록이 잘 조회되는지")
    @Test
    void WhenGetMemberFollowingList_ThenShouldReturnOk() throws Exception {

        MemberDto.FollowingResponse response = MemberDto.FollowingResponse.builder()
            .content(List.of(MemberDto.FollowInfo.builder()
                .memberId(1L)
                .nickname("test user")
                .profileImageUrl("https://test.yigil.co.kr/images/profile.jpg")
                .build())
            )
            .hasNext(false)
            .build();

        when(memberFacade.getFollowingList(anyLong(), any(PageRequest.class))).thenReturn(
            mock(MemberInfo.FollowingResponse.class));
        when(memberDtoMapper.of(any(MemberInfo.FollowingResponse.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/members/followings/{memberId}", 1L)
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "members/get-member-following-list",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("content[].member_id").description("회원 ID"),
                    fieldWithPath("content[].nickname").description("닉네임"),
                    fieldWithPath("content[].profile_image_url").description("프로필 이미지 URL"),
                    fieldWithPath("has_next").description("다음 페이지 존재 여부")
                )
            ));

        verify(memberFacade).getFollowingList(anyLong(), any(PageRequest.class));
    }
}