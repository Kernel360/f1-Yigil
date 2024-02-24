package kr.co.yigil.travel.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.travel.application.SpotFacade;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.interfaces.dto.SpotInfoDto;
import kr.co.yigil.travel.interfaces.dto.mapper.SpotMapper;
import kr.co.yigil.travel.interfaces.dto.response.SpotsInPlaceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(SpotApiController.class)
@AutoConfigureRestDocs
public class SpotApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SpotFacade spotFacade;

    @MockBean
    private SpotMapper spotMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @DisplayName("getSpotsInPlace가 잘 동작하는지")
    @Test
    void getSpotsInPlace_ShouldReturnOk() throws Exception {
        Member member = new Member(1L, "test@test.com", "12345", "오너 닉네임", "image/ownerProfile.jpg", SocialLoginType.KAKAO);

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
