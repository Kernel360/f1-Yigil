package kr.co.yigil.travel.interfaces.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.travel.application.SpotFacade;
import kr.co.yigil.travel.interfaces.dto.mapper.SpotMapper;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(SpotApiController.class)
public class SpotApiControllerTest {

    private MockMvc mockMvc;


    @MockBean
    private SpotFacade spotFacade;

    @MockBean
    private SpotMapper spotMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("getSpotsInPlace가 잘 동작하는지")
    @Test
    void getSpotsInPlace_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/spots/place/{placeId}", 1L)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "createdAt")
                        .param("sortOrder", "desc"))
                .andExpect(status().isOk());

        verify(spotFacade).getSpotSliceInPlace(anyLong(), any(Pageable.class));
    }

    @DisplayName("registerSpot 메서드가 잘 동작하는지")
    @Test
    void registerSpot_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/spots")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(spotFacade).registerSpot(any(), anyLong());
    }

    @DisplayName("retrieveSpot 메서드가 잘 동작하는지")
    @Test
    void retrieveSpot_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/spots/{spotId}", 1L))
                .andExpect(status().isOk());

        verify(spotFacade).retrieveSpotInfo(anyLong());
    }

    @DisplayName("updateSpot 메서드가 잘 동작하는지")
    @Test
    void updateSpot_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/spots/{spotId}", 1L)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(spotFacade).modifySpot(any(), anyLong(), anyLong());
    }

    @DisplayName("deleteSpot 메서드가 잘 동작하는지")
    @Test
    void deleteSpot_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/v1/spots/{spotId}", 1L))
                .andExpect(status().isOk());

        verify(spotFacade).deleteSpot(anyLong(), anyLong());
    }

}
