package kr.co.yigil.bookmark.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

public interface BookmarkService {

    void addBookmark(Long memberId, Long placeId);

    void deleteBookmark(Long memberId, Long placeId);

    Slice<BookmarkInfo> getBookmarkSlice(Long memberId, PageRequest pageRequest);
}
