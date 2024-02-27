package kr.co.yigil.bookmark.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.bookmark.interfaces.dto.BookmarkInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarksResponse {
    private List<BookmarkInfoDto> bookmarks;
    private boolean hasNext;
}
