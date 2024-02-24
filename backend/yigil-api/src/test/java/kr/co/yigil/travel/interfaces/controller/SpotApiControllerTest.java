package kr.co.yigil.travel.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import kr.co.yigil.travel.application.SpotFacade;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.interfaces.dto.SpotInfoDto;
import kr.co.yigil.travel.interfaces.dto.mapper.SpotMapper;
import kr.co.yigil.travel.interfaces.dto.response.SpotsInPlaceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
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
        Slice<Spot> mockSlice = mock(Slice.class);
        SpotInfoDto spotInfo = new SpotInfoDto(List.of("images/image.png", "images/photo.jpeg"), "images/profile.jpg", "오너 닉네임", "4.5", "2024-02-01");
        SpotsInPlaceResponse response = new SpotsInPlaceResponse(List.of(spotInfo), false);

        when(spotFacade.getSpotSliceInPlace(anyLong(), any(Pageable.class))).thenReturn(mockSlice);
        when(spotMapper.spotsSliceToSpotInPlaceResponse(mockSlice)).thenReturn(response);

        mockMvc.perform(get("/api/v1/spots/place/{placeId}", 1L)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "createdAt")
                        .param("sortOrder", "desc"))
                .andExpect(status().isOk())
                        .andDo(document(
                                "spots/get-spots-in-place",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("placeId").description("장소 아이디")
                                ),
                                queryParameters(
                                        parameterWithName("page").description("현재 페이지").optional(),
                                        parameterWithName("size").description("페이지 크기").optional(),
                                        parameterWithName("sortBy").description("정렬 옵션").optional(),
                                        parameterWithName("sortOrder").description("정렬 순서").optional()
                                ),
                                responseFields(
                                        fieldWithPath("has_next").type(JsonFieldType.BOOLEAN).description("다음 페이지가 있는지 여부"),
                                        subsectionWithPath("spots").description("spot의 정보"),
                                        fieldWithPath("spots[].image_url_list").description("imageUrl의 List"),
                                        fieldWithPath("spots[].owner_profile_image_url").type(JsonFieldType.STRING).description("Spot 등록 사용자의 프로필 이미지 Url"),
                                        fieldWithPath("spots[].owner_nickname").type(JsonFieldType.STRING).description("Spot 등록 사용자의 닉네임"),
                                        fieldWithPath("spots[].rate").type(JsonFieldType.STRING).description("Spot의 평점"),
                                        fieldWithPath("spots[].create_date").type(JsonFieldType.STRING).description("Spot의 생성일시")
                                )
                        ));

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
