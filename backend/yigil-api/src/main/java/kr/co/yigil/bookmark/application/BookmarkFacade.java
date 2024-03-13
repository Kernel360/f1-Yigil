package kr.co.yigil.bookmark.application;

import kr.co.yigil.bookmark.domain.BookmarkService;
import kr.co.yigil.bookmark.domain.dto.BookmarkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkFacade {
    private final BookmarkService bookmarkService;

    public void addBookmark(Long memberId, Long placeId) {
        bookmarkService.addBookmark(memberId, placeId);
    }

    public void deleteBookmark(Long memberId, Long placeId) {
        bookmarkService.deleteBookmark(memberId, placeId);
    }

    public Slice<BookmarkDto> getBookmarkSlice(Long memberId, PageRequest pageRequest) {
        return bookmarkService.getBookmarkSlice(memberId, pageRequest);
    }
}