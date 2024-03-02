package kr.co.yigil.comment.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.comment.application.CommentFacade;
import kr.co.yigil.comment.domain.CommentCommand;
import kr.co.yigil.comment.domain.CommentInfo;
import kr.co.yigil.comment.interfaces.dto.CommentDto;
import kr.co.yigil.comment.interfaces.dto.CommentDto.CommentCreateResponse;
import kr.co.yigil.comment.interfaces.dto.mapper.CommentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(CommentApiController.class)
@EnableSpringDataWebSupport
class CommentApiControllerTest {

    @MockBean
    private CommentFacade commentFacade;
    @MockBean
    private CommentMapper commentMapper;

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

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

    @DisplayName("comment 생성 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenCreateComment_thenReturns200AndCommentCreateResponse() throws Exception {
        CommentDto.CommentCreateResponse mockResponse = new CommentCreateResponse("message");

        Accessor accessor = Accessor.member(1L);

        CommentDto.CommentCreateRequest request = new CommentDto.CommentCreateRequest("content", 1L,
            1L);

        String json = objectMapper.writeValueAsString(request);

        when(commentMapper.of(any(CommentDto.CommentCreateRequest.class))).thenReturn(
            mock(CommentCommand.CommentCreateRequest.class));
        when(commentFacade.createComment(anyLong(), anyLong(),
            any(CommentCommand.CommentCreateRequest.class))).thenReturn(
            mock(CommentInfo.CommentCreateResponse.class));
        when(commentMapper.of(any(CommentInfo.CommentCreateResponse.class))).thenReturn(
            mockResponse);

        mockMvc.perform(post("/api/v1/comments/travels/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .sessionAttr("memberId", accessor.getMemberId()))
            .andExpect(status().isOk())
            .andDo(document(
                "comments/comment-create",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("content").description("댓글 내용"),
                    fieldWithPath("parentId").description("부모 댓글 id"),
                    fieldWithPath("notifiedMemberId").description("알림을 받을 회원 id")
                ),
                responseFields(
                    fieldWithPath("message").description("응답 메시지")
                )
            ));
    }

    @DisplayName("부모 댓글 조회 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetParentCommentList_thenReturns200AndCommentResponse() throws Exception {

        CommentDto.CommentsUnitInfo mockUnitInfo1 = new CommentDto.CommentsUnitInfo(
            1L, "content", 1L, "nickname", "http://yigil.co.kr/images/profile.jpg", 3,
            "2024-03-01");

        CommentDto.CommentsUnitInfo mockUnitInfo2 = new CommentDto.CommentsUnitInfo(
            2L, "content2", 1L, "nickname3", "http://yigil.co.kr/images/profile3.jpg", 1,
            "2024-03-01");
        List<CommentDto.CommentsUnitInfo> commentsUnitInfoList = List.of(mockUnitInfo1,
            mockUnitInfo2);

        CommentDto.CommentsResponse mockResponse = new CommentDto.CommentsResponse(
            commentsUnitInfoList, false);

        when(commentFacade.getParentCommentList(anyLong(), any(Pageable.class))).thenReturn(
            mock(CommentInfo.CommentsResponse.class));
        when(commentMapper.of(any(CommentInfo.CommentsResponse.class))).thenReturn(
            mockResponse);

        mockMvc.perform(get("/api/v1/comments/travels/{travelId}" ,1L)
                .param("page", "1")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document(
                "comments/comment-get-parent",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("travelId").description("본문 id")
                ),
                queryParameters(
                    parameterWithName("page").description("페이지 번호"),
                    parameterWithName("size").description("페이지 크기")
                ),
                responseFields(
                    subsectionWithPath("content").description("comment의 정보"),
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("댓글 id"),
                    fieldWithPath("content[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                    fieldWithPath("content[].member_id").type(JsonFieldType.NUMBER).description("회원 id"),
                    fieldWithPath("content[].member_nickname").type(JsonFieldType.STRING).description("닉네임"),
                    fieldWithPath("content[].member_image_url").type(JsonFieldType.STRING).description("프로필 이미지 url"),
                    fieldWithPath("content[].child_count").type(JsonFieldType.NUMBER).description("자식 댓글 수"),
                    fieldWithPath("content[].created_at").type(JsonFieldType.STRING).description("생성일"),
                    fieldWithPath("has_next").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                )
            ));
    }

    @DisplayName("대댓글 조회 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetChildCommentList_thenReturns200AndCommentResponse() throws Exception {

            CommentDto.CommentsUnitInfo mockUnitInfo1 = new CommentDto.CommentsUnitInfo(
                1L, "content", 1L, "nickname", "http://yigil.co.kr/images/profile.jpg", 0,
                "2024-03-01");

            CommentDto.CommentsUnitInfo mockUnitInfo2 = new CommentDto.CommentsUnitInfo(
                2L, "content2", 1L, "nickname3", "http://yigil.co.kr/images/profile3.jpg", 0,
                "2024-03-01");
            List<CommentDto.CommentsUnitInfo> commentsUnitInfoList = List.of(mockUnitInfo1,
                mockUnitInfo2);

            CommentDto.CommentsResponse mockResponse = new CommentDto.CommentsResponse(
                commentsUnitInfoList, false);

            when(commentFacade.getChildCommentList(anyLong(), any(Pageable.class))).thenReturn(
                mock(CommentInfo.CommentsResponse.class));
            when(commentMapper.of(any(CommentInfo.CommentsResponse.class))).thenReturn(
                mockResponse);

            mockMvc.perform(get("/api/v1/comments/parents/{parentId}" ,1L)
                    .param("page", "1")
                    .param("size", "5")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(
                    "comments/comment-get-child",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    pathParameters(
                        parameterWithName("parentId").description("부모 댓글 id")
                    ),
                    queryParameters(
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("페이지 크기")
                    ),
                    responseFields(

                        subsectionWithPath("content").description("comment의 정보"),
                        fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("댓글 id"),
                        fieldWithPath("content[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                        fieldWithPath("content[].member_id").type(JsonFieldType.NUMBER).description("회원 id"),
                        fieldWithPath("content[].member_nickname").type(JsonFieldType.STRING).description("닉네임"),
                        fieldWithPath("content[].member_image_url").type(JsonFieldType.STRING).description("프로필 이미지"),
                        fieldWithPath("content[].child_count").type(JsonFieldType.NUMBER).description("자식 댓글 수"),
                        fieldWithPath("content[].created_at").type(JsonFieldType.STRING).description("생성일"),
                        fieldWithPath("has_next").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부")
                    )
                ));
    }

    @DisplayName("comment 수정 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenUpdateComment_thenReturns200AndCommentUpdateResponse() throws Exception {
        CommentDto.CommentUpdateRequest request = new CommentDto.CommentUpdateRequest("content", 1L,
            1L);

        String json = objectMapper.writeValueAsString(request);

        when(commentMapper.of(any(CommentDto.CommentUpdateRequest.class))).thenReturn(
            mock(CommentCommand.CommentUpdateRequest.class));


        mockMvc.perform(post("/api/v1/comments/{commentId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andDo(document(
                "comments/comment-update",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("commentId").description("댓글 id")
                ),
                requestFields(
                    fieldWithPath("content").description("댓글 내용"),
                    fieldWithPath("parentId").description("부모 댓글 id"),
                    fieldWithPath("notifiedMemberId").description("알림을 받을 회원 id")
                ),
                responseFields(
                    fieldWithPath("message").description("응답 메시지")
                )
            ));

        verify(commentFacade).updateComment(anyLong(), anyLong(), any(CommentCommand.CommentUpdateRequest.class));
    }

    @DisplayName("comment 삭제 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenDeleteComment_thenReturns200AndCommentDeleteResponse() throws Exception {

        CommentDto.CommentDeleteResponse mockResponse = CommentDto.CommentDeleteResponse.builder()
            .message("댓글 삭제 성공")
            .build();

        when(commentFacade.deleteComment(anyLong(), anyLong())).thenReturn(
            mock(CommentInfo.DeleteResponse.class));
        when(commentMapper.of(any(CommentInfo.DeleteResponse.class))).thenReturn(
            mockResponse);

        mockMvc.perform(delete("/api/v1/comments/{commentId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document(
                "comments/comment-delete",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("commentId").description("댓글 id")
                ),
                responseFields(
                    fieldWithPath("message").description("응답 메시지")
                )
            ));
    }
}