package kr.co.yigil.travel.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kr.co.yigil.travel.application.SpotFacade;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotInfo;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpot;
import kr.co.yigil.travel.interfaces.dto.SpotDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.SpotInfoDto;
import kr.co.yigil.travel.interfaces.dto.mapper.SpotMapper;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
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
import org.springframework.mock.web.MockMultipartFile;
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

    @DisplayName("getMySpotInPlace 메서드가 잘 동작하는지")
    @Test
    void getMySpotInPlace_ShouldReturnOk() throws Exception {
        MySpot mockInfo = mock(MySpot.class);
        MySpotInPlaceResponse mockResponse = new MySpotInPlaceResponse(true, "4.5", List.of("images/image.jpg", "images/thumb.png"), "2024-02-05", "내가 쓴 리뷰리뷰리뷰");

        when(spotFacade.retrieveMySpotInfoInPlace(anyLong(), anyLong())).thenReturn(mockInfo);
        when(spotMapper.toMySpotInPlaceResponse(mockInfo)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/spots/place/{placeId}/me", 1L))
                .andExpect(status().isOk())
                .andDo(document(
                        "spots/get-my-spot-in-place",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 아이디")
                        ),
                        responseFields(
                                fieldWithPath("exists").type(JsonFieldType.BOOLEAN).description("스팟이 존재하는지 여부"),
                                fieldWithPath("rate").type(JsonFieldType.STRING).description("스팟의 평점 정보"),
                                fieldWithPath("image_urls").type(JsonFieldType.ARRAY).description("스팟 관련 이미지의 url 배열"),
                                fieldWithPath("create_date").type(JsonFieldType.STRING).description("스팟의 생성 일자"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("스팟의 본문")
                        )
                ));

        verify(spotFacade).retrieveMySpotInfoInPlace(anyLong(), anyLong());
    }

    @DisplayName("registerSpot 메서드가 잘 동작하는지")
    @Test
    void registerSpot_ShouldReturnOk() throws Exception {
        MockMultipartFile image1 = new MockMultipartFile("image", "image.jpg", "image/jpeg", "<<jpg data>>".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("pic", "pic.jpg", "image/jpeg", "<<jpg data>>".getBytes());
        MockMultipartFile mapStaticImage = new MockMultipartFile("mapStatic", "mapStatic.png", "image/png", "<<png data>>".getBytes());
        MockMultipartFile placeImage = new MockMultipartFile("placeImg", "placeImg.png", "image/png", "<<png data>>".getBytes());

        mockMvc.perform(multipart("/api/v1/spots")
                        .file("files", image1.getBytes())
                        .file("files", image2.getBytes())
                        .file("mapStaticImageFile", mapStaticImage.getBytes())
                        .file("placeImageFile", placeImage.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("pointJson", "{ \"type\" : \"Point\", \"coordinates\": [ 555,  555 ] }")
                        .param("title", "스팟 타이틀")
                        .param("description", "스팟 본문")
                        .param("rate", "5.0")
                        .param("placeName", "장소 타이틀")
                        .param("placeAddress", "장소구 장소면 장소리")
                        .param("placePointJson", "{ \"type\" : \"Point\", \"coordinates\": [ 555,  555 ] }")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(document(
                                "spots/regist-spot",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestParts(
                                        partWithName("files").description("Spot의 이미지 파일 (다중파일)"),
                                        partWithName("mapStaticImageFile").description("Spot의 장소를 나타내는 지도 이미지 파일(필수x)"),
                                        partWithName("placeImageFile").description("Spot의 장소를 나타내는 썸네일 이미지 파일(필수x)")
                                ),
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답의 본문 메시지")
                                )
                        ));

        verify(spotFacade).registerSpot(any(), anyLong());
    }

    @DisplayName("retrieveSpot 메서드가 잘 동작하는지")
    @Test
    void retrieveSpot_ShouldReturnOk() throws Exception {
        SpotInfo.Main mockInfo = mock(SpotInfo.Main.class);
        SpotDetailInfoDto mockResponse = new SpotDetailInfoDto("장소명", "3.0", "장소시 장소구 장소동", "images/mapstatic.png", List.of("images/spot.png", "images/spot.jpeg"), "2024-02-01", "스팟 설명");
        when(spotFacade.retrieveSpotInfo(anyLong())).thenReturn(mockInfo);
        when(spotMapper.toSpotDetailInfoDto(mockInfo)).thenReturn(mockResponse);
        mockMvc.perform(get("/api/v1/spots/{spotId}", 1L))
                .andExpect(status().isOk())
                        .andDo(document(
                                "spots/retrieve-spot",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("spotId").description("스팟 아이디")
                                ),
                                responseFields(
                                    fieldWithPath("place_name").type(JsonFieldType.STRING).description("스팟 관련 장소 명"),
                                    fieldWithPath("rate").type(JsonFieldType.STRING).description("스팟의 평점 정보"),
                                    fieldWithPath("place_address").type(JsonFieldType.STRING).description("스팟 관련 장소의 주소"),
                                    fieldWithPath("map_static_image_file_url").type(JsonFieldType.STRING).description("스팟의 위치를 나타내는 이미지 파일의 상대경로"),
                                    fieldWithPath("image_urls").type(JsonFieldType.ARRAY).description("스팟 관련 이미지의 상대 경로 배열"),
                                    fieldWithPath("create_date").type(JsonFieldType.STRING).description("스팟의 생성 일자"),
                                    fieldWithPath("description").type(JsonFieldType.STRING).description("스팟의 본문 정보")
                                )
                        ));

        verify(spotFacade).retrieveSpotInfo(anyLong());
    }

    @DisplayName("updateSpot 메서드가 잘 동작하는지")
    @Test
    void updateSpot_ShouldReturnOk() throws Exception {
        mockMvc.perform(multipart("/api/v1/spots/{spotId}", 1L)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                        .andDo(document(
                                "spots/update-spot",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("spotId").description("스팟 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답의 본문 메시지")
                                )
                        ));

        verify(spotFacade).modifySpot(any(), anyLong(), anyLong());
    }

    @DisplayName("deleteSpot 메서드가 잘 동작하는지")
    @Test
    void deleteSpot_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/v1/spots/{spotId}", 1L))
                .andExpect(status().isOk())
                        .andDo(document(
                                "spots/delete-spot",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("spotId").description("스팟 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답의 본문 메시지")
                                )
                        ));

        verify(spotFacade).deleteSpot(anyLong(), anyLong());
    }

}
