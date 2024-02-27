package kr.co.yigil.bookmark.domain;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookmarkServiceImplTest {

    @Mock
    private BookmarkReader bookmarkReader;

    @Mock
    private MemberReader memberReader;

    @Mock
    private PlaceReader placeReader;

    @Mock
    private BookmarkStore bookmarkStore;

    @InjectMocks
    private BookmarkServiceImpl bookmarkServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("유효한 파라미터로 addBookmark 메서드가 잘 호출되는지")
    @Test
    void whenAddBookmark_valid_thenCallsMethod() {
        Long memberId = 1L;
        Long placeId = 2L;

        when(bookmarkReader.isBookmarked(anyLong(), anyLong())).thenReturn(false);

        Member mockMember = mock(Member.class);
        when(memberReader.getMember(anyLong())).thenReturn(mockMember);

        Place mockPlace = mock(Place.class);
        when(placeReader.getPlace(anyLong())).thenReturn(mockPlace);

        bookmarkServiceImpl.addBookmark(memberId, placeId);

        verify(bookmarkStore, times(1)).store(any(Member.class), any(Place.class));
    }

    @DisplayName("이미 북마크된 장소에 대해 addBookmark를 호출 시 예외가 잘 발생되는지")
    @Test
    void whenAddBookmark_alreadyBookmarked_thenThrowsException() {
        Long memberId = 1L;
        Long placeId = 2L;

        when(bookmarkReader.isBookmarked(anyLong(), anyLong())).thenReturn(true);

        assertThrows(
                BadRequestException.class, () -> bookmarkServiceImpl.addBookmark(memberId, placeId));
    }

    @DisplayName("유효한 파라미터로 deleteBookmark 메서드가 잘 호출되는지")
    @Test
    void whenDeleteBookmark_valid_thenCallsMethod() {
        Long memberId = 1L;
        Long placeId = 2L;

        Member mockMember = mock(Member.class);
        when(memberReader.getMember(anyLong())).thenReturn(mockMember);

        Place mockPlace = mock(Place.class);
        when(placeReader.getPlace(anyLong())).thenReturn(mockPlace);

        bookmarkServiceImpl.deleteBookmark(memberId, placeId);

        verify(bookmarkStore, times(1)).remove(any(Member.class), any(Place.class));
    }
}