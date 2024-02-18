package kr.co.yigil.travel.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import kr.co.yigil.travel.application.SpotService;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotDeleteResponse;
import kr.co.yigil.travel.dto.response.SpotInfoResponse;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(SpotController.class)
public class SpotControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private SpotController spotController;

    @MockBean
    private SpotService spotService;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("Spot 몫록 조회 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void GivenValidParameter_WhenGetSpotList_ThenReturnResponse200Ok() throws Exception {
        when(spotService.getSpotList(anyLong(), any(Pageable.class))).thenReturn(new SliceImpl<>(
            new ArrayList<>(), PageRequest.of(5,5), true));

        mockMvc.perform(get("/api/v1/spots/places/1"))
            .andExpect(status().isOk());
    }

    @DisplayName("Spot 생성 요청이 왔을 때 201 응답과 response가 잘 반환되는지")
    @Test
    public void createSpotTest() throws Exception {
        when(spotService.createSpot(anyLong(), any(SpotCreateRequest.class)))
            .thenReturn(new SpotCreateResponse());

        String jsonContent = "{"
            + "\"title\":\"Test Spot\","
            + "\"description\":\"This is a test spot\","
            + "\"location\":\"{\\\"type\\\": \\\"Point\\\", \\\"coordinates\\\": [1, 2]}\""
            + "}";

        mockMvc.perform(post("/api/v1/spots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .sessionAttr("memberId", 1L))
            .andExpect(status().isCreated());
    }

    @DisplayName("Spot 정보 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    public void getSpotInfoTest() throws Exception {
        when(spotService.getSpotInfo(anyLong())).thenReturn(new SpotInfoResponse());

        mockMvc.perform(get("/api/v1/spots/1"))
            .andExpect(status().isOk());
    }

    @DisplayName("Spot 업데이트 요청이 왔을 때 301 응답과 response가 잘 반환되는지")
    @Test
    public void updateSpotTest() throws Exception {
        when(spotService.updateSpot(anyLong(), anyLong(), any(SpotUpdateRequest.class)))
            .thenReturn(new SpotUpdateResponse());

        String jsonContent = "{"
            + "\"title\":\"Updated Spot\","
            + "\"description\":\"This is an updated test spot\","
            + "\"location\":\"{\\\"type\\\": \\\"Point\\\", \\\"coordinates\\\": [3, 4]}\""
            + "}";

        mockMvc.perform(post("/api/v1/spots/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .sessionAttr("memberId", 1L))
            .andExpect(status().isOk());
    }

    @DisplayName("Spot 삭제 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    public void deleteSpotTest() throws Exception {
        when(spotService.deleteSpot(anyLong(), anyLong())).thenReturn(new SpotDeleteResponse());

        mockMvc.perform(delete("/api/v1/spots/1")
                .sessionAttr("memberId", 1L))
            .andExpect(status().isOk());
    }
}
