package kr.co.yigil.bookmark.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.bookmark.application.BookmarkFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(BookmarkApiController.class)
@AutoConfigureRestDocs
class BookmarkApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private BookmarkFacade bookmarkFacade;

    @InjectMocks
    private BookmarkApiController bookmarkApiController;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @DisplayName("북마크 추가 요청시 200 응답과 response가 잘 반환되는지")
    @Test
    void whenAddBookmark_thenReturns200AndAddBookmarkResponse() throws Exception {
        Long placeId = 1L;

        mockMvc.perform(post("/api/v1/add-bookmark/{place_id}", placeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"장소 북마크 추가 성공\"}"))
                .andDo(document("bookmarks/add-bookmark",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("place_id").description("북마크할 장소 아이디")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메시지")
                        )
                ));

    }

    @DisplayName("북마크 삭제 요청시 200 응답과 response가 잘 반환되는지")
    @Test
    void whenDeleteBookmark_thenReturns200AndDeleteBookmarkResponse() throws Exception {
        Long placeId = 1L;

        mockMvc.perform(post("/api/v1/delete-bookmark/{place_id}", placeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"장소 북마크 제거 성공\"}"))
                .andDo(document("bookmarks/delete-bookmark",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("place_id").description("북마크 취소할 장소 아이디")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메시지")
                        )
                ));
    }
}