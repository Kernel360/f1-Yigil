package kr.co.yigil.travel.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.travel.application.TravelFacade;
import kr.co.yigil.travel.interfaces.dto.request.ChangeStatusTravelRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(TravelApiController.class)
@AutoConfigureRestDocs
public class TravelApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private TravelFacade travelFacade;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @DisplayName("changeOnPublicTravel 메서드가 잘 동작하는지")
    @Test
    void changeOnPublicTravel_ReturnsOk() throws Exception {

        mockMvc.perform(post("/api/v1/travels/change-on-public")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"travel_id\":1}"))
                .andExpect(status().isOk())
                .andDo(document(
                        "travels/change-on-public",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("travel_id").type(JsonFieldType.NUMBER).description("travel의 id")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답의 본문 메시지")
                        )
                ));

        verify(travelFacade).changeOnPublicTravel(anyLong(), anyLong());
    }

    @DisplayName("changeOnPrivateTravel 메서드가 잘 동작하는지")
    @Test
    void changeOnPrivateTravel_ReturnsOk() throws Exception {

        mockMvc.perform(post("/api/v1/travels/change-on-private")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"travel_id\":2}"))
                .andExpect(status().isOk())
                .andDo(document(
                "travels/change-on-private",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("travel_id").type(JsonFieldType.NUMBER).description("travel의 id")
                ),
                responseFields(
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답의 본문 메시지")
                )
        ));

        verify(travelFacade).changeOnPrivateTravel(anyLong(), anyLong());
    }
}
