package kr.co.yigil.statistics.interfaces.controller;


import kr.co.yigil.statistics.application.StatisticsFacade;
import kr.co.yigil.statistics.domain.StaticInfo;
import kr.co.yigil.statistics.interfaces.dto.StatisticsDto;
import kr.co.yigil.statistics.interfaces.dto.StatisticsMapper;
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
@WebMvcTest(StatisticsApiController.class)
class StatisticsApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private StatisticsFacade statisticsFacade;
    @MockBean
    private StatisticsMapper statisticsMapper;


    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("통계 조회 요청이 들어왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void givenTravelTypeSpot_whenRequestGetDailyFavors_thenShouldReturn200AndResponse() throws Exception {

        when(statisticsFacade.getDailyFavors(any(LocalDate.class), any(LocalDate.class), any(TravelType.class), any(Pageable.class))
        ).thenReturn(mock(StaticInfo.DailyFavorsInfo.class));
        when(statisticsMapper.toDailyFavorsResponse(any(StaticInfo.DailyFavorsInfo.class))).thenReturn(mock(StatisticsDto.DailyFavorsResponse.class));

        mockMvc.perform(get("/api/v1/statistics/daily-favors")
                        .param("travelType", "spot")
                        .param("startDate", "2024-03-21")
                        .param("endDate", "2024-03-22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("통계 조회 요청이 들어왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void GivenTravelTypeCourse_whenRequestGetDailyFavors_thenShouldReturn200AndResponse() throws Exception {

        when(statisticsFacade.getDailyFavors(any(LocalDate.class), any(LocalDate.class), any(TravelType.class), any(Pageable.class))
        ).thenReturn(mock(StaticInfo.DailyFavorsInfo.class));
        when(statisticsMapper.toDailyFavorsResponse(any(StaticInfo.DailyFavorsInfo.class))).thenReturn(mock(StatisticsDto.DailyFavorsResponse.class));

        mockMvc.perform(get("/api/v1/statistics/daily-favors")
                        .param("travelType", "course")
                        .param("startDate", "2024-03-21")
                        .param("endDate", "2024-03-22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("통계 조회 요청이 들어왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void GivenTravelTypeALL_whenRequestGetDailyFavors_thenShouldReturn200AndResponse() throws Exception {

        when(statisticsFacade.getDailyFavors(any(LocalDate.class), any(LocalDate.class), any(TravelType.class), any(Pageable.class))
        ).thenReturn(mock(StaticInfo.DailyFavorsInfo.class));
        when(statisticsMapper.toDailyFavorsResponse(any(StaticInfo.DailyFavorsInfo.class))).thenReturn(mock(StatisticsDto.DailyFavorsResponse.class));

        mockMvc.perform(get("/api/v1/statistics/daily-favors")
                        .param("travelType", "all")
                        .param("startDate", "2024-03-21")
                        .param("endDate", "2024-03-22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("TravelType이 주어지지 않은 상황에서 통계 조회 요청이 들어왔을 때 200 응답이 반환되는지")
    @Test
    void GivenTravelTypeNULL_whenRequestGetDailyFavors_thenShouldReturn400AndResponse() throws Exception {

        when(statisticsFacade.getDailyFavors(any(LocalDate.class), any(LocalDate.class), any(TravelType.class), any(Pageable.class))
        ).thenReturn(mock(StaticInfo.DailyFavorsInfo.class));
        when(statisticsMapper.toDailyFavorsResponse(any(StaticInfo.DailyFavorsInfo.class))).thenReturn(mock(StatisticsDto.DailyFavorsResponse.class));

        mockMvc.perform(get("/api/v1/statistics/daily-favors")
                        .param("startDate", "2024-03-21")
                        .param("endDate", "2024-03-22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("상위 n 개의 통계 조회 요청이 들어왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void givenTravelTypeNull_whenGetTopDailyFavors_thenShouldReturn200AndResponse() throws Exception {

        mockMvc.perform(get("/api/v1/statistics/daily-favors/top")
                        .param("startDate", "2024-03-21")
                        .param("endDate", "2024-03-22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}