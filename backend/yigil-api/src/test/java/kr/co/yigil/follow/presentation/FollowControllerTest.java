package kr.co.yigil.follow.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.follow.application.FollowService;
import kr.co.yigil.follow.dto.response.FollowResponse;
import kr.co.yigil.follow.dto.response.UnfollowResponse;
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
    private FollowService followService;

    @InjectMocks
    private FollowController followController;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("팔로우가 요청되었을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenFollow_thenReturns200AndFollowResponse() throws Exception {
        Long accessorId = 1L;
        Long memberId = 2L;
        FollowResponse mockResponse = new FollowResponse();

        given(followService.follow(accessorId, memberId)).willReturn(mockResponse);

        mockMvc.perform(post("/api/v1/follow/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("언팔로우가 요청되었을 때 200 응답과 Response가 잘 반환되는지")
    @Test
    void whenUnfollow_thenReturns200AndUnfollowResponse() throws Exception {
        Long accessorId = 1L;
        Long memberId = 2L;
        UnfollowResponse mockResponse = new UnfollowResponse();

        given(followService.unfollow(accessorId, memberId)).willReturn(mockResponse);

        mockMvc.perform(post("/api/v1/unfollow/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
