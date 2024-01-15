package kr.co.yigil.like.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.like.application.LikeService;
import kr.co.yigil.like.dto.response.LikeResponse;
import kr.co.yigil.like.dto.response.UnlikeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LikeController.class)
public class LikeControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("좋아요가 요청되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenLike_thenReturns200AndLikeResponse() throws Exception {
        Long accessorId = 1L;
        Long postId = 1L;
        LikeResponse mockResponse = new LikeResponse();

        given(likeService.like(accessorId, postId)).willReturn(mockResponse);

        mockMvc.perform(post("/api/v1/like/" + postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("좋아요 취소가 요청되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenUnlike_thenReturns200AndUnlikeResponse() throws Exception {
        Long accessorId = 1L;
        Long postId = 1L;
        UnlikeResponse mockResponse = new UnlikeResponse();

        given(likeService.unlike(accessorId, postId)).willReturn(mockResponse);

        mockMvc.perform(post("/api/v1/unlike/" + postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
