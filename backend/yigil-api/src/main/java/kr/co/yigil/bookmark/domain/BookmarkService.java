package kr.co.yigil.bookmark.domain;

import kr.co.yigil.bookmark.domain.dto.BookmarkDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

public interface BookmarkService {

    void addBookmark(Long memberId, Long placeId);

    void deleteBookmark(Long memberId, Long placeId);

    Slice<BookmarkDto> getBookmarkSlice(Long memberId, PageRequest pageRequest);
}
