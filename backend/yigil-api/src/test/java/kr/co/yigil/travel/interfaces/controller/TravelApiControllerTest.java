package kr.co.yigil.travel.interfaces.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.yigil.travel.application.TravelFacade;
import kr.co.yigil.travel.interfaces.dto.request.ChangeStatusTravelRequest;
import kr.co.yigil.travel.interfaces.dto.response.ChangeStatusTravelResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TravelApiController.class)
public class TravelApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private TravelFacade travelFacade;


    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("changeOnPublicTravel 메서드가 잘 동작하는지")
    @Test
    void changeOnPublicTravel_ReturnsOk() throws Exception {
        ChangeStatusTravelRequest request = new ChangeStatusTravelRequest(1L);
        Long accessorId = 1L;

        mockMvc.perform(post("/api/v1/travels/change-on-public")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"travelId\":1}"))
                .andExpect(status().isOk());
    }

    @DisplayName("changeOnPrivateTravel 메서드가 잘 동작하는지")
    @Test
    void changeOnPrivateTravel_ReturnsOk() throws Exception {
        ChangeStatusTravelRequest request = new ChangeStatusTravelRequest(2L);

        mockMvc.perform(post("/api/v1/travels/change-on-private")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"travelId\":2}"))
                .andExpect(status().isOk());
    }
}
