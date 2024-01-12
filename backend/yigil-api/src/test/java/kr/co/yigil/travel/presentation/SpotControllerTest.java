package kr.co.yigil.travel.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.SpotService;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotFindResponse;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SpotController.class)
class SpotControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SpotService spotService;

    @InjectMocks
    private SpotController spotController;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("spot 게시글 생성 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenCreateSpotPost_thenReturns201AndSpotCreateResponse() throws Exception {
        SpotCreateResponse mockResponse = new SpotCreateResponse();
        Accessor accessor = Accessor.member(1L);

        given(spotService.createSpot(anyLong(), any(SpotCreateRequest.class))).willReturn(mockResponse);
        MockMultipartFile imageFile = new MockMultipartFile("file", "filename.jpg", "image/jpeg", new byte[10]);
        MockMultipartFile videoFile = new MockMultipartFile("file2", "filename2.mp4", "video/mp4", new byte[10]);

        mockMvc.perform(multipart("/api/v1/spots")
                .file(imageFile)
                .file(videoFile)
                .param("pointJson","pointJson")
                .param("title","title")
                .param("description", "description")
                .sessionAttr("memberId", accessor.getMemberId()))
            .andExpect(status().isOk());
    }



    @DisplayName("spot 게시글이 조회될 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetSpotPost_thenReturns200AndSpotFindResponse() throws Exception {
        SpotFindResponse mockResponse = new SpotFindResponse();
        given(spotService.findSpotByPostId(anyLong())).willReturn(mockResponse);
        mockMvc.perform(get("/api/v1/spots/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @DisplayName("spot 게시글이 업데이트 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenUpdateSpotPost_thenReturns200AndSpotUpdateResponse() throws Exception {
        SpotUpdateResponse mockResponse = new SpotUpdateResponse();
        SpotUpdateRequest mockRequest = new SpotUpdateRequest();
        Accessor accessor = Accessor.member(1L);
        Long postId = 1L;
        given(spotService.updateSpot(accessor.getMemberId(), postId, mockRequest)).willReturn(mockResponse);
        MockMultipartFile imageFile = new MockMultipartFile("file", "filename.jpg", "image/jpeg", new byte[10]);
        MockMultipartFile videoFile = new MockMultipartFile("file2", "filename2.mp4", "video/mp4", new byte[10]);
        mockMvc.perform(multipart("/api/v1/spots/"+ postId)
                .file(imageFile)
                .file(videoFile)
                .param("pointJson","pointJson").param("title","title")
                .param("description", "description")
                .sessionAttr("memberId", accessor.getMemberId()))
            .andExpect(status().isOk());
    }
}