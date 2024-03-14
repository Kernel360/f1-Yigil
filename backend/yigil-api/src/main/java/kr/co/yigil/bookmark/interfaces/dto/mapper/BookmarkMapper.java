package kr.co.yigil.bookmark.interfaces.dto.mapper;

import java.util.List;
import kr.co.yigil.bookmark.domain.BookmarkInfo;
import kr.co.yigil.bookmark.interfaces.dto.BookmarkInfoDto;
import kr.co.yigil.bookmark.interfaces.dto.response.BookmarksResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    default BookmarksResponse toDto(Slice<BookmarkInfo> bookmarkInfoSlice) {

        List<BookmarkInfoDto> bookmarkInfoDtoList = bookmarkInfoSlice.getContent().stream()
            .map(this::bookmarkToBookmarkInfoDto)
            .toList();

        return new BookmarksResponse(bookmarkInfoDtoList, bookmarkInfoSlice.hasNext());
    }

    BookmarkInfoDto bookmarkToBookmarkInfoDto(BookmarkInfo bookmark);
}
