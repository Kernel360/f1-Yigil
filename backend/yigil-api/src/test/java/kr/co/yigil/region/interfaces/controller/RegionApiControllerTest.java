package kr.co.yigil.region.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kr.co.yigil.region.application.RegionFacade;
import kr.co.yigil.region.domain.RegionInfo.Category;
import kr.co.yigil.region.domain.RegionInfo.Main;
import kr.co.yigil.region.interfaces.dto.MyRegionDto;
import kr.co.yigil.region.interfaces.dto.RegionCategoryDto;
import kr.co.yigil.region.interfaces.dto.RegionDto;
import kr.co.yigil.region.interfaces.dto.mapper.RegionMapper;
import kr.co.yigil.region.interfaces.dto.response.MyRegionResponse;
import kr.co.yigil.region.interfaces.dto.response.RegionSelectResponse;
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
@WebMvcTest(RegionApiController.class)
@AutoConfigureRestDocs
public class RegionApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RegionFacade regionFacade;

    @MockBean
    private RegionMapper regionMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider)).build();
    }

    @DisplayName("getRegionSelect 메서드가 잘 동작하는지")
    @Test
    void getRegionSelect_ReturnsOk() throws Exception {
        RegionDto mockRegion = new RegionDto(1L, "홍대 | 와플", true);
        RegionCategoryDto mockCategory = new RegionCategoryDto("서울 북부", List.of(mockRegion));
        RegionSelectResponse mockResponse = new RegionSelectResponse(List.of(mockCategory));

        Category mockInfo = mock(Category.class);
        when(regionFacade.getRegionSelectList(anyLong())).thenReturn(List.of(mockInfo));
        when(regionMapper.toRegionSelectResponse(List.of(mockInfo))).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/regions/select")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(
                        "regions/region-select-form",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                            subsectionWithPath("categories").description("지역별 카테고리 정보"),
                                fieldWithPath("categories[].category_name").type(JsonFieldType.STRING).description("지역별 카테고리 명"),
                                subsectionWithPath("categories[].regions").description("카테고리 내 지역 정보"),
                                fieldWithPath("categories[].regions[].id").type(JsonFieldType.NUMBER).description("지역의 고유 id"),
                                fieldWithPath("categories[].regions[].region_name").type(JsonFieldType.STRING).description("지역명"),
                                fieldWithPath("categories[].regions[].selected").type(JsonFieldType.BOOLEAN).description("사용자의 해당 지역의 관심지역 설정 여부")
                        )
                ));
    }

    @DisplayName("getMyRegion 메서드가 잘 동작하는지")
    @Test
    void getMyRegion_ReturnsOk() throws Exception {
        MyRegionDto mockRegion = new MyRegionDto(1L, "홍대 | 상수");
        MyRegionResponse mockResponse = new MyRegionResponse(List.of(mockRegion));

        Main mockInfo = mock(Main.class);
        when(regionMapper.toMyRegionResponse(List.of(mockInfo))).thenReturn(mockResponse);

        when(regionFacade.getMyRegions(anyLong())).thenReturn(List.of(mockInfo));

        mockMvc.perform(get("/api/v1/regions/my")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(
                        "regions/my-region",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                subsectionWithPath("regions").description("지역 정보"),
                                fieldWithPath("regions[].id").type(JsonFieldType.NUMBER).description("지역의 고유 Id"),
                                fieldWithPath("regions[].name").type(JsonFieldType.STRING).description("지역의 이름")
                        )
                ));
    }
}
