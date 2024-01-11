package kr.co.yigil.post.presentation;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.post.dto.response.PostDeleteResponse;
import kr.co.yigil.post.dto.response.PostListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PostController.class)
class PostControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("Find All Posts - Success")
    @Test
    void whenFindAllPosts_thenReturnPostListResponse() throws Exception {
        PostListResponse mockResponse = new PostListResponse(Collections.emptyList());
        given(postService.findAllPosts()).willReturn(mockResponse);

        mockMvc.perform(get("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @DisplayName("Delete Post - Success")
    @Test
    void whenDeletePost_thenReturnPostDeleteResponse() throws Exception {
        PostDeleteResponse mockResponse = new PostDeleteResponse("Post deleted successfully");
        Accessor accessor = Accessor.member(1L);

        given(postService.deletePost(anyLong(), anyLong())).willReturn(mockResponse);

        mockMvc.perform(delete("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .sessionAttr("memberId", accessor.getMemberId()))
            .andExpect(status().isOk());
    }
}
