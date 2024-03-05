package kr.co.yigil.favor.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.favor.application.FavorFacade;
import kr.co.yigil.favor.domain.FavorInfo;
import kr.co.yigil.favor.domain.FavorInfo.AddFavorResponse;
import kr.co.yigil.favor.domain.FavorInfo.DeleteFavorResponse;
import kr.co.yigil.favor.interfaces.dto.FavorDto;
import kr.co.yigil.favor.interfaces.dto.mapper.FavorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(FavorApiController.class)
@EnableSpringDataWebSupport
class FavorApiControllerTest {

    @MockBean
    private FavorFacade favorFacade;

    @MockBean
    private FavorMapper favorMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation)
                .uris()
                .withScheme("https")
                .withHost("yigil.co.kr")
                .withPort(80)
            )
            .build();
    }

    @DisplayName("좋아요 추가가 잘 되는지")
    @Test
    void WhenAddFavor_ThenShouldReturnOk() throws Exception {
        FavorDto.AddFavorResponse response = FavorDto.AddFavorResponse.builder()
            .message("좋아요가 완료되었습니다.")
            .build();

        Long memberId = 1L;
        Long travelId = 1L;

        FavorInfo.AddFavorResponse addFavorResponse = new FavorInfo.AddFavorResponse("좋아요가 완료되었습니다.");

        when(favorFacade.addFavor(anyLong(), anyLong())).thenReturn(addFavorResponse);
        when(favorMapper.of(any(AddFavorResponse.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/like/{travelId}", travelId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("likes/add-favor",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("travelId").description("좋아요를 추가할 게시물의 ID")
                ),
                responseFields(
                    fieldWithPath("message").description("좋아요가 완료되었습니다.")
                )
            ));
    }

    @DisplayName("좋아요 취소 잘 되는지")
    @Test
    void WhenDeleteFavor_ThenShouldReturnOk() throws Exception{
        FavorDto.DeleteFavorResponse response = FavorDto.DeleteFavorResponse.builder()
            .message("좋아요가 취소되었습니다.")
            .build();

        Long travelId = 1L;

        FavorInfo.DeleteFavorResponse deleteFavorResponse = new FavorInfo.DeleteFavorResponse("좋아요가 취소되었습니다.");

        when(favorFacade.deleteFavor(anyLong(), anyLong())).thenReturn(deleteFavorResponse);
        when(favorMapper.of(any(DeleteFavorResponse.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/unlike/{travelId}", travelId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("likes/delete-favor",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("travelId").description("좋아요를 취소할 게시물의 ID")
                ),
                responseFields(
                    fieldWithPath("message").description("좋아요가 취소되었습니다.")
                )
            ));
    }
}