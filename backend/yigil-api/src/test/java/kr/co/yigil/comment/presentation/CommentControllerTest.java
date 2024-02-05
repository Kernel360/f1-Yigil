package kr.co.yigil.comment.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.dto.request.CommentCreateRequest;
import kr.co.yigil.comment.dto.response.CommentCreateResponse;
import kr.co.yigil.comment.dto.response.CommentDeleteResponse;
import kr.co.yigil.comment.dto.response.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(CommentController.class)
@ExtendWith({MockitoExtension.class, SpringExtension.class})
class CommentControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("comment 생성 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenCreateComment_thenReturns200AndCommentCreateResponse() throws Exception {
        CommentCreateResponse mockResponse = new CommentCreateResponse();
        Accessor accessor = Accessor.member(1L);

        CommentCreateRequest request = new CommentCreateRequest("content", 1L, 1L);

        given(commentService.createComment(anyLong(), anyLong(),
            any(CommentCreateRequest.class))).willReturn(mockResponse);

        mockMvc.perform(post("/api/v1/comments/travels/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
                .sessionAttr("memberId", accessor.getMemberId()))
            .andExpect(status().isOk());
    }

    @DisplayName("댓글 조회 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetParentCommentList_thenReturns200AndCommentResponse() throws Exception {
        CommentResponse mockCommentResponse1 = new CommentResponse();
        CommentResponse mockCommentResponse2 = new CommentResponse();

        given(commentService.getParentCommentList(anyLong(), any(Pageable.class))).willReturn(
            new SliceImpl<>(List.of(mockCommentResponse1, mockCommentResponse2),
                PageRequest.of(5, 5), true));

        mockMvc.perform(get("/api/v1/comments/travels/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @DisplayName("대댓글 조회 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetChildCommentList_thenReturns200AndCommentResponse() throws Exception {
        CommentResponse mockCommentResponse1 = new CommentResponse();
        CommentResponse mockCommentResponse2 = new CommentResponse();

        given(commentService.getChildCommentList(anyLong(), any(Pageable.class))).willReturn(
            new SliceImpl<>(List.of(mockCommentResponse1, mockCommentResponse2), PageRequest.of(5, 5), true)
        );

        mockMvc.perform(get("/api/v1/comments/parents/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @DisplayName("comment 삭제 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenDeleteComment_thenReturns200AndCommentDeleteResponse() throws Exception {
        CommentDeleteResponse mockResponse = new CommentDeleteResponse();

        given(commentService.deleteComment(anyLong(), anyLong(), anyLong())).willReturn(
            mockResponse);

        mockMvc.perform(delete("/api/v1/comments/travels/1/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
