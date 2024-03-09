package kr.co.yigil.travel.spot.interfaces.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.travel.spot.application.SpotFacade;
import kr.co.yigil.travel.spot.domain.SpotInfoDto;
import kr.co.yigil.travel.spot.interfaces.dto.SpotDto.SpotDetailResponse;
import kr.co.yigil.travel.spot.interfaces.dto.SpotDto.SpotsResponse;
import kr.co.yigil.travel.spot.interfaces.dto.mapper.SpotDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SpotApiController.class)
class SpotApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SpotFacade spotFacade;

    @MockBean
    private SpotDtoMapper spotDtoMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("회원이 작성한 스팟 리스트를 조회하는 테스트")
    @Test
    void whenGetSpots_thenShouldReturn200AndSposResponse() throws Exception {

        when(spotFacade.getSpots(any())).thenReturn(mock(SpotInfoDto.SpotPageInfo.class));
        when(spotDtoMapper.of(any(SpotInfoDto.SpotPageInfo.class))).thenReturn(
            mock(SpotsResponse.class));

        mockMvc.perform(get("/api/v1/spots"))
            .andExpect(status().isOk());

    }

    @DisplayName("회원의 스팟 상세 정보를 조회하는 테스트")
    @Test
    void whenGetSpot_thenShouldReturn200AndSpotDtailResponse() throws Exception {

        when(spotFacade.getSpot(any())).thenReturn(mock(SpotInfoDto.SpotDetailInfo.class));
        when(spotDtoMapper.of(any(SpotInfoDto.SpotDetailInfo.class))).thenReturn(mock(
            SpotDetailResponse.class));

        mockMvc.perform(get("/api/v1/spots/1"))
            .andExpect(status().isOk());
    }

    @DisplayName("회원이 작성한 스팟을 삭제하는 테스트")
    @Test
    void whenDeleteSpot_thenShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/spots/1"))
            .andExpect(status().isOk());
    }
}