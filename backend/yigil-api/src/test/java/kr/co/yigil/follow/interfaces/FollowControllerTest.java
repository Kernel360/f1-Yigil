package kr.co.yigil.follow.interfaces;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.follow.application.FollowFacade;
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
@WebMvcTest(FollowController.class)
public class FollowControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private FollowFacade followFacade;

    @InjectMocks
    private FollowController followController;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("팔로우가 요청되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenFollow_thenReturns200AndFollowResponse() throws Exception {
        Long memberId = 2L;

        mockMvc.perform(post("/api/v1/follow/" + memberId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"팔로우 성공\"}"));

    }

    @DisplayName("언팔로우가 요청되었을 때 200 응답과 Response가 잘 반환되는지")
    @Test
    void whenUnfollow_thenReturns200AndUnfollowResponse() throws Exception {
        Long memberId = 2L;


        mockMvc.perform(post("/api/v1/unfollow/" + memberId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"언팔로우 성공\"}"));

    }
}