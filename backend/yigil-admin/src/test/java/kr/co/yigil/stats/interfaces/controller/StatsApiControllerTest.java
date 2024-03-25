package kr.co.yigil.stats.interfaces.controller;

import kr.co.yigil.stats.application.StatsFacade;
import kr.co.yigil.stats.domain.StaticInfo;
import kr.co.yigil.stats.interfaces.dto.StatisticsDto;
import kr.co.yigil.stats.interfaces.dto.mapper.StatsMapper;
import kr.co.yigil.travel.TravelType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StatsApiController.class)
class StatsApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private StatsFacade statsFacade;

    @MockBean
    private StatsMapper statsMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("통계 조회 요청이 들어왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenSendStatsRequest_thenReturns200AndStatsResponse() throws Exception {

        mockMvc.perform(get("/api/v1/stats/region")
                        .param("startDate", "2022-01-01")
                        .param("endDate", "2022-01-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("통계 조회 요청이 들어왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void givenTravelTypeSpot_whenRequestGetDailyFavors_thenShouldReturn200AndResponse() throws Exception {

        when(statsFacade.getDailyFavors(any(LocalDate.class), any(LocalDate.class), any(TravelType.class), any(Pageable.class))
        ).thenReturn(mock(StaticInfo.DailyFavorsInfo.class));
        when(statsMapper.toDailyFavorsResponse(any(StaticInfo.DailyFavorsInfo.class))).thenReturn(mock(StatisticsDto.DailyFavorsResponse.class));

        mockMvc.perform(get("/api/v1/stats/daily-favors")
                        .param("travelType", "spot")
                        .param("startDate", "2024-03-21")
                        .param("endDate", "2024-03-22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("통계 조회 요청이 들어왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void GivenTravelTypeCourse_whenRequestGetDailyFavors_thenShouldReturn200AndResponse() throws Exception {

        when(statsFacade.getDailyFavors(any(LocalDate.class), any(LocalDate.class), any(TravelType.class), any(Pageable.class))
        ).thenReturn(mock(StaticInfo.DailyFavorsInfo.class));
        when(statsMapper.toDailyFavorsResponse(any(StaticInfo.DailyFavorsInfo.class))).thenReturn(mock(StatisticsDto.DailyFavorsResponse.class));

        mockMvc.perform(get("/api/v1/stats/daily-favors")
                        .param("travelType", "course")
                        .param("startDate", "2024-03-21")
                        .param("endDate", "2024-03-22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("통계 조회 요청이 들어왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void GivenTravelTypeALL_whenRequestGetDailyFavors_thenShouldReturn200AndResponse() throws Exception {

        when(statsFacade.getDailyFavors(any(LocalDate.class), any(LocalDate.class), any(TravelType.class), any(Pageable.class))
        ).thenReturn(mock(StaticInfo.DailyFavorsInfo.class));
        when(statsMapper.toDailyFavorsResponse(any(StaticInfo.DailyFavorsInfo.class))).thenReturn(mock(StatisticsDto.DailyFavorsResponse.class));

        mockMvc.perform(get("/api/v1/stats/daily-favors")
                        .param("travelType", "all")
                        .param("startDate", "2024-03-21")
                        .param("endDate", "2024-03-22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("TravelType이 주어지지 않은 상황에서 통계 조회 요청이 들어왔을 때 200 응답이 반환되는지")
    @Test
    void GivenTravelTypeNULL_whenRequestGetDailyFavors_thenShouldReturn400AndResponse() throws Exception {

        when(statsFacade.getDailyFavors(any(LocalDate.class), any(LocalDate.class), any(TravelType.class), any(Pageable.class))
        ).thenReturn(mock(StaticInfo.DailyFavorsInfo.class));
        when(statsMapper.toDailyFavorsResponse(any(StaticInfo.DailyFavorsInfo.class))).thenReturn(mock(StatisticsDto.DailyFavorsResponse.class));

        mockMvc.perform(get("/api/v1/stats/daily-favors")
                        .param("startDate", "2024-03-21")
                        .param("endDate", "2024-03-22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("상위 n 개의 통계 조회 요청이 들어왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void givenTravelTypeNull_whenGetTopDailyFavors_thenShouldReturn200AndResponse() throws Exception {

        mockMvc.perform(get("/api/v1/stats/daily-favors/top")
                        .param("startDate", "2024-03-21")
                        .param("endDate", "2024-03-22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}