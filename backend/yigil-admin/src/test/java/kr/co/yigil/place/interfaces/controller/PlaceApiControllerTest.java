package kr.co.yigil.place.interfaces.controller;

import kr.co.yigil.place.application.PlaceFacade;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand;
import kr.co.yigil.place.interfaces.dto.mapper.PlaceMapper;
import kr.co.yigil.place.interfaces.dto.response.PlaceDetailResponse;
import kr.co.yigil.place.interfaces.dto.response.PlacesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)

@WebMvcTest(PlaceApiController.class)
class PlaceApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PlaceFacade placeFacade;
    @MockBean
    private PlaceMapper placeMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @DisplayName("장소 리스트를 조회하는 테스트")
    @Test
    void whenGetPlaces_thenShouldReturn200AndResponse() throws Exception {
        when(placeFacade.getPlaces(any())).thenReturn(mock(Page.class));
        when(placeMapper.toResponse(any(Page.class))).thenReturn(mock(PlacesResponse.class));

        mockMvc.perform(get("/api/v1/places"))
            .andExpect(status().isOk());
    }

    @Test
    void getPlaceDetail() throws Exception {
        when(placeFacade.getPlaceDetail(any())).thenReturn(mock(Place.class));
        when(placeMapper.toResponse(any(Place.class))).thenReturn(mock(PlaceDetailResponse.class));

        mockMvc.perform(get("/api/v1/places/1"))
            .andExpect(status().isOk());
    }

    @Test
    void updateImage() throws Exception {

        when(placeMapper.toCommand(any())).thenReturn(mock(PlaceCommand.UpdateImageCommand.class));

        mockMvc.perform(post("/api/v1/places/update-image"))
            .andExpect(status().isOk());

        verify(placeFacade, times(1)).updateImage(any());
    }
}