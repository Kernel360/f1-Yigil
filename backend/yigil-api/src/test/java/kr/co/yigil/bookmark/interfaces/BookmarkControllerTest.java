package kr.co.yigil.bookmark.interfaces;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.bookmark.application.BookmarkFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookmarkController.class)
class BookmarkControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private BookmarkFacade bookmarkFacade;

    @InjectMocks
    private BookmarkController bookmarkController;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("북마크 추가 요청시 200 응답과 response가 잘 반환되는지")
    @Test
    void whenAddBookmark_thenReturns200AndAddBookmarkResponse() throws Exception {
        Long placeId = 1L;

        mockMvc.perform(post("/api/v1/add-bookmark/" + placeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"장소 북마크 추가 성공\"}"));
    }

    @DisplayName("북마크 삭제 요청시 200 응답과 response가 잘 반환되는지")
    @Test
    void whenDeleteBookmark_thenReturns200AndDeleteBookmarkResponse() throws Exception {
        Long placeId = 1L;

        mockMvc.perform(post("/api/v1/delete-bookmark/" + placeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"장소 북마크 제거 성공\"}"));
    }
}