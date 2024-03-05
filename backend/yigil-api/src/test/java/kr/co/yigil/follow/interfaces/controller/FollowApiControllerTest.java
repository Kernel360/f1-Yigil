package kr.co.yigil.follow.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kr.co.yigil.follow.application.FollowFacade;
import kr.co.yigil.follow.domain.FollowInfo;
import kr.co.yigil.follow.interfaces.dto.FollowDto.FollowerInfo;
import kr.co.yigil.follow.interfaces.dto.FollowDto.FollowersResponse;
import kr.co.yigil.follow.interfaces.dto.FollowDto.FollowingInfo;
import kr.co.yigil.follow.interfaces.dto.FollowDto.FollowingsResponse;
import kr.co.yigil.follow.interfaces.dto.FollowDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(FollowApiController.class)
@AutoConfigureRestDocs
public class FollowApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private FollowFacade followFacade;

    @MockBean
    private FollowDtoMapper followDtoMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation)).build();
    }

    @DisplayName("팔로우가 요청되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenFollow_thenReturns200AndFollowResponse() throws Exception {
        Long memberId = 2L;

        mockMvc.perform(post("/api/v1/follows/follow/{member_id}", memberId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"message\":\"팔로우 성공\"}"))
            .andDo(document("follows/follow",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("member_id").description("팔로우할 회원 아이디")
                ),
                responseFields(
                    fieldWithPath("message").description("응답 메시지")
                )
            ));

        verify(followFacade).follow(anyLong(), anyLong());
    }

    @DisplayName("언팔로우가 요청되었을 때 200 응답과 Response가 잘 반환되는지")
    @Test
    void whenUnfollow_thenReturns200AndUnfollowResponse() throws Exception {
        Long memberId = 2L;

        mockMvc.perform(post("/api/v1/follows/unfollow/{member_id}", memberId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"message\":\"언팔로우 성공\"}"))
            .andDo(document("follows/unfollow",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("member_id").description("언팔로우할 회원 아이디")
                ),
                responseFields(
                    fieldWithPath("message").description("응답 메시지")
                )
            ));

        verify(followFacade).unfollow(anyLong(), anyLong());
    }

    @DisplayName("내 팔로우 목록 조회가 잘 되는지")
    @Test
    void WhenGetMyFollowerList_ThenShouldReturnOk() throws Exception {

        FollowersResponse response = FollowersResponse.builder()
            .content(List.of(FollowerInfo.builder()
                .memberId(1L)
                .nickname("test user")
                .profileImageUrl("https://cdn.yigil.co.kr/images/profile.jpg")
                .following(true)
                .build())
            )
            .hasNext(false)
            .build();

        when(followFacade.getFollowerList(anyLong(), any(PageRequest.class))).thenReturn(
            mock(FollowInfo.FollowersResponse.class));
        when(followDtoMapper.of(any(FollowInfo.FollowersResponse.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/follows/followers")
                .param("page", "1")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "follows/get-my-follower-list",
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
                    fieldWithPath("content[].following").description("팔로우 여부"),
                    fieldWithPath("has_next").description("다음 페이지 존재 여부")
                )

            ));
        verify(followFacade).getFollowerList(anyLong(), any(PageRequest.class));
    }

    @DisplayName("팔로잉 목록 조회가 잘 되는지")
    @Test
    void WhenGetMyFollowingList_ThenShouldReturnOk() throws Exception {

        FollowingsResponse response = FollowingsResponse.builder()
            .content(List.of(FollowingInfo.builder()
                .memberId(1L)
                .nickname("test user")
                .profileImageUrl("https://cdn.yigil.co.kr/images/profile.jpg")
                .build())
            )
            .hasNext(false)
            .build();

        when(followFacade.getFollowingList(anyLong(), any(PageRequest.class))).thenReturn(
            mock(FollowInfo.FollowingsResponse.class));
        when(followDtoMapper.of(any(FollowInfo.FollowingsResponse.class))).thenReturn(response);
        mockMvc.perform(get("/api/v1/follows/followings")
                .param("page", "1")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "follows/get-my-following-list",
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
        verify(followFacade).getFollowingList(anyLong(), any(PageRequest.class));
    }

    @DisplayName("회원을 팔로우하는 목록이 잘 조회되는지")
    @Test
    void WhenGetMemberFollowerList_ThenReturnOk() throws Exception {

        FollowersResponse response = FollowersResponse.builder()
            .content(List.of(FollowerInfo.builder()
                .memberId(1L)
                .nickname("test user")
                .profileImageUrl("https://cdn.yigil.co.kr/images/profile.jpg")
                .following(true)
                .build())
            )
            .hasNext(false)
            .build();

        when(followFacade.getFollowerList(anyLong(), any(PageRequest.class))).thenReturn(
            mock(FollowInfo.FollowersResponse.class));
        when(followDtoMapper.of(any(FollowInfo.FollowersResponse.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/follows/{memberId}/followers", 1L)
                .param("page", "1")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "follows/get-member-follower-list",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("content[].member_id").description("회원 ID"),
                    fieldWithPath("content[].nickname").description("닉네임"),
                    fieldWithPath("content[].profile_image_url").description("프로필 이미지 URL"),
                    fieldWithPath("content[].following").description("팔로우 여부"),
                    fieldWithPath("has_next").description("다음 페이지 존재 여부")
                )
            ));

        verify(followFacade).getFollowerList(anyLong(), any(PageRequest.class));

    }

    @DisplayName("회원이 팔로우하는 목록이 잘 조회되는지")
    @Test
    void WhenGetMemberFollowingList_ThenShouldReturnOk() throws Exception {

        FollowingsResponse response = FollowingsResponse.builder()
            .content(List.of(FollowingInfo.builder()
                .memberId(1L)
                .nickname("test user")
                .profileImageUrl("https://cdn.yigil.co.kr/images/images/profile.jpg")
                .build())
            )
            .hasNext(false)
            .build();

        when(followFacade.getFollowingList(anyLong(), any(PageRequest.class))).thenReturn(
            mock(FollowInfo.FollowingsResponse.class));
        when(followDtoMapper.of(any(FollowInfo.FollowingsResponse.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/follows/{memberId}/followings", 1L)
                .param("page", "1")
                .param("size", "5")
                .param("sortBy", "id")
                .param("sortOrder", "asc")
            )
            .andExpect(status().isOk())
            .andDo(document(
                "follows/get-member-following-list",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("content[].member_id").description("회원 ID"),
                    fieldWithPath("content[].nickname").description("닉네임"),
                    fieldWithPath("content[].profile_image_url").description("프로필 이미지 URL"),
                    fieldWithPath("has_next").description("다음 페이지 존재 여부")
                )
            ));

        verify(followFacade).getFollowingList(anyLong(), any(PageRequest.class));
    }

}