package kr.co.yigil.bookmark.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.bookmark.interfaces.dto.BookmarkInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookmarksResponse {
    private List<BookmarkInfoDto> bookmarks;
    private boolean hasNext;

public BookmarksResponse(List<BookmarkInfoDto> bookmarks, boolean hasNext) {
        this.bookmarks = bookmarks;
        this.hasNext = hasNext;
    }
}
