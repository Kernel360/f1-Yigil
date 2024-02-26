package kr.co.yigil.bookmark.application;

import kr.co.yigil.bookmark.domain.BookmarkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BookmarkFacadeTest {

    @Mock
    private BookmarkService bookmarkService;

    @InjectMocks
    private BookmarkFacade bookmarkFacade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("addBookmark 메서드가 BookmarkService의 메서드를 잘 호출하는지")
    @Test
    void whenAddBookmark_thenCallsMethods() {
        Long memberId = 1L;
        Long placeId = 2L;

        bookmarkFacade.addBookmark(memberId, placeId);

        verify(bookmarkService, times(1)).addBookmark(memberId, placeId);
    }

    @DisplayName("deleteBookmark 메서드가 BookmarkService의 메서드를 잘 호출하는지")
    @Test
    void whenDeleteBookmark_thenCallsMethods() {
        Long memberId = 1L;
        Long placeId = 2L;

        bookmarkFacade.deleteBookmark(memberId, placeId);

        verify(bookmarkService, times(1)).deleteBookmark(memberId, placeId);
    }
}