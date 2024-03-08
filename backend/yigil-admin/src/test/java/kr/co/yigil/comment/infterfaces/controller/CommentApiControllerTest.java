package kr.co.yigil.comment.infterfaces.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.comment.application.CommentFacade;
import kr.co.yigil.comment.domain.CommentInfo;
import kr.co.yigil.comment.infterfaces.dto.CommentDto;
import kr.co.yigil.comment.infterfaces.dto.mapper.CommentMapper;
import kr.co.yigil.global.SortBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentApiController.class)
class CommentApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CommentFacade commentFacade;

    @MockBean
    private CommentMapper commentMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("댓글 목록을 조회했을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetParentCommentList_thenShouldReturn200AndParentCommentsResponse() throws Exception {

        when(commentFacade.getParentComments(1L, PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, SortBy.CREATED_AT.getValue()))))
            .thenReturn(mock(CommentInfo.ParentPageComments.class));
        when(commentMapper.of(any(CommentInfo.ParentPageComments.class)))
            .thenReturn(mock(CommentDto.ParentCommentsResponse.class));

        mockMvc.perform(get("/api/v1/comments/{travel_id}/parents", 1L)
            .param("travel_id", "1")
            .param("size", "5")
            .param("page", "1"))
            .andExpect(status().isOk());
    }

    @DisplayName("대댓글 목록을 조회했을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetChildrenCommentList_thenShouldReturn200AndChildrenCommentsResponse() throws Exception{

        when(commentFacade.getChildrenComments(1L, PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, SortBy.CREATED_AT.getValue())))
        ).thenReturn(mock(CommentInfo.ChildrenPageComments.class));
        when(commentMapper.of(any(CommentInfo.ChildrenPageComments.class))
        ).thenReturn(mock(CommentDto.ChildrenCommentsResponse.class));

        mockMvc.perform(get("/api/v1/comments/{parent_id}/children", 1L)
            .param("parent_id", "1")
            .param("size", "5")
            .param("page", "1"))
            .andExpect(status().isOk());
    }

    @DisplayName("댓글을 삭제했을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenDeleteComment_thenShouldReturn200AndString() throws Exception {
        // given
        Long commentId = 1L;

        // then
        mockMvc.perform(delete("/api/v1/comments/{comment_id}", commentId))
            .andExpect(status().isOk());

        verify(commentFacade).deleteComment(commentId);
    }
}