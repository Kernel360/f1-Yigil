package kr.co.yigil.bookmark.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.print.Book;
import java.util.List;
import kr.co.yigil.bookmark.application.BookmarkFacade;
import kr.co.yigil.bookmark.domain.Bookmark;
import kr.co.yigil.bookmark.interfaces.dto.BookmarkInfoDto;
import kr.co.yigil.bookmark.interfaces.dto.mapper.BookmarkMapper;
import kr.co.yigil.bookmark.interfaces.dto.response.BookmarksResponse;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.domain.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
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

    @MockBean
    private BookmarkMapper bookmarkMapper;

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

    @DisplayName("북마크 조회 요청시 200 응답과 response가 잘 반환되는지")
    @Test
    void whenGetBookmarks_thenReturns200AndBookmarksResponse() throws Exception {
        Bookmark bookmark = new Bookmark(mock(Member.class), mock(Place.class));
        PageRequest pageRequest = PageRequest.of(0, 5);
        Slice<Bookmark> bookmarkSlice = new SliceImpl<>(List.of(bookmark), pageRequest, true);
        when(bookmarkFacade.getBookmarkSlice(anyLong(), any(PageRequest.class))).thenReturn(bookmarkSlice);

        BookmarkInfoDto bookmarkInfoDto = new BookmarkInfoDto(1L, "placeName", "placeImage", 5.0, true);
        BookmarksResponse bookmarksResponse = new BookmarksResponse(List.of(bookmarkInfoDto), true);
        when(bookmarkMapper.bookmarkSliceToBookmarksResponse(bookmarkSlice)).thenReturn(bookmarksResponse);
        mockMvc.perform(get("/api/v1/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("bookmarks/get-bookmarks",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("page").description("현재 페이지").optional(),
                                parameterWithName("size").description("페이지 크기").optional(),
                                parameterWithName("sortBy").description("정렬 옵션").optional(),
                                parameterWithName("sortOrder").description("정렬 순서").optional()
                        ),
                        responseFields(
                                fieldWithPath("has_next").type(JsonFieldType.BOOLEAN).description("다음 페이지가 있는지 여부"),
                                subsectionWithPath("bookmarks").description("Bookmark의 정보"),
                                fieldWithPath("bookmarks[].place_id").description("Bookmark한 장소 아이디"),
                                fieldWithPath("bookmarks[].place_name").description("Bookmark한 장소 이름"),
                                fieldWithPath("bookmarks[].place_image").description("Bookmark한 장소 이미지 URL"),
                                fieldWithPath("bookmarks[].rate").description("Bookmark한 장소 평점"),
                                fieldWithPath("bookmarks[].is_bookmarked").description("북마크 여부 .. 이거 꼭 필요한까요? 필요없다면 나중에 빼겠습니다ㅎㅎ")
                        )
                ));
        verify(bookmarkFacade).getBookmarkSlice(anyLong(), any(PageRequest.class));
        verify(bookmarkMapper).bookmarkSliceToBookmarksResponse(bookmarkSlice);

    }
}