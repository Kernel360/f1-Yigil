package kr.co.yigil.bookmark.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.bookmark.domain.dto.BookmarkDto;
import kr.co.yigil.member.domain.MemberReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class BookmarkReaderImplTest {

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private BookmarkQueryRepository bookmarkQueryRepository;

    @Mock
    private MemberReader memberReader;

    @InjectMocks
    private BookmarkReaderImpl bookmarkReader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("getBookmarkSlice 메서드가 올바른 Slice를 반환하는지")
    @Test
    void whenGetBookmarkSlice_thenReturnsCorrectSlice() {
        Long memberId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        BookmarkDto bookmarkDto = new BookmarkDto(1L, 2L, "placeName", "placeImage", 4.5);
        Slice<BookmarkDto> expectedSlice = new SliceImpl<>(List.of(bookmarkDto));

        when(bookmarkQueryRepository.findAllByMemberId(anyLong(),  any(PageRequest.class))).thenReturn(expectedSlice);

        Slice<BookmarkDto> actualSlice = bookmarkReader.getBookmarkSlice(memberId, pageable);

        assertEquals(expectedSlice, actualSlice);
    }

    @DisplayName("isBookmarked 메서드가 올바른 결과를 반환하는지")
    @Test
    void whenIsBookmarked_thenReturnsCorrectResult() {
        Long memberId = 1L;
        Long placeId = 2L;

        when(bookmarkRepository.existsByMemberIdAndPlaceId(memberId, placeId)).thenReturn(true);

        boolean isBookmarked = bookmarkReader.isBookmarked(memberId, placeId);

        assertTrue(isBookmarked);
    }
}