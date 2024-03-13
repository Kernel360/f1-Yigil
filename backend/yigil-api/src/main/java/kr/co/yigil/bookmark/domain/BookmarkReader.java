package kr.co.yigil.bookmark.domain;

import kr.co.yigil.bookmark.domain.dto.BookmarkDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BookmarkReader {
    Slice<BookmarkDto> getBookmarkSlice(Long memberId, Pageable pageable);

    boolean isBookmarked(Long memberId, Long placeId);
}
