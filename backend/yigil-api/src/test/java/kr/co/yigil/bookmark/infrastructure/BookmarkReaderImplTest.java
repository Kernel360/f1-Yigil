package kr.co.yigil.bookmark.infrastructure;

import kr.co.yigil.bookmark.domain.Bookmark;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class BookmarkReaderImplTest {

    @Mock
    private BookmarkRepository bookmarkRepository;

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
        Member member = new Member("kiit0901@gmail.com", "123456", "stone", "profile.jpg", "kakao");
        List<Bookmark> bookmarks = new ArrayList<>();
        Slice<Bookmark> expectedSlice = new SliceImpl<>(bookmarks);

        when(memberReader.getMember(memberId)).thenReturn(member);
        when(bookmarkRepository.findAllByMember(member)).thenReturn(expectedSlice);

        Slice<Bookmark> actualSlice = bookmarkReader.getBookmarkSlice(memberId);

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