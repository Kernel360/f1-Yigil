package kr.co.yigil.bookmark.domain;

import java.util.List;
import org.springframework.data.domain.Slice;

public interface BookmarkReader {
    Slice<Bookmark> getBookmarkSlice(Long memberId);

    boolean isBookmarked(Long memberId, Long placeId);
}
