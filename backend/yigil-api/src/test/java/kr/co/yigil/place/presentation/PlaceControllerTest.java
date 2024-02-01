package kr.co.yigil.place.presentation;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.place.application.PlaceService;
import kr.co.yigil.place.dto.response.PlaceFindResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(PlaceController.class)
public class PlaceControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeService;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @DisplayName("Place 정보 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    public void getPlaceTest() throws Exception {
        when(placeService.getPlace(anyLong())).thenReturn(new PlaceFindResponse());

        mockMvc.perform(get("/api/v1/places/1"))
                .andExpect(status().isOk());
    }
}
