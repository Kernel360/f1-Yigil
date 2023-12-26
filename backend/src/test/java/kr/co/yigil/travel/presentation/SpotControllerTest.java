package kr.co.yigil.travel.presentation;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.SpotService;
import kr.co.yigil.travel.dto.response.SpotFindResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
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

    @DisplayName("spot 게시글이 조회될 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetSpotPost_thenReturns200AndSpotFindResponse() throws Exception {
        SpotFindResponse mockResponse = new SpotFindResponse();
        Accessor accessor = Accessor.member(1L);

//        given(spotService.findSpot())
    }
//    @DisplayName("")
//    @Test
//    void createSpotPost() {
//        SpotCreateRequest spotRequest = new SpotCreateRequest();
//        spotRequest.setPointJson("{ \"type\": \"Point\", \"feature\": [30, 10]}");
//        spotRequest.setIsInCourse(true);
//
//
//    }

    @Test
    void findSpot() {
    }

    @Test
    void updateSpot() {
    }

    @Test
    void deletePost() {
    }
}