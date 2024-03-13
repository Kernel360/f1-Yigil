package kr.co.yigil.bookmark.interfaces.dto.mapper;

import java.util.List;
import kr.co.yigil.bookmark.domain.dto.BookmarkDto;
import kr.co.yigil.bookmark.interfaces.dto.BookmarkInfoDto;
import kr.co.yigil.bookmark.interfaces.dto.response.BookmarksResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

        default BookmarksResponse bookmarkSliceToBookmarksResponse(Slice<BookmarkDto> bookmarkSlice) {
        List<BookmarkInfoDto> bookmarkInfoDtoList = bookmarksToBookmarkInfoDtoList(
                bookmarkSlice.getContent());
        boolean hasNext = bookmarkSlice.hasNext();
        return new BookmarksResponse(bookmarkInfoDtoList, hasNext);
    }


    default List<BookmarkInfoDto> bookmarksToBookmarkInfoDtoList(List<BookmarkDto> bookmarks) {
        return bookmarks.stream()
            .map(this::bookmarkToBookmarkInfoDto)
            .toList();
    }

    @Mapping(target = "bookmarkId", source = "bookmarkId")
    @Mapping(target = "placeId", source = "placeId")
    @Mapping(target = "placeName", source = "placeName")
    @Mapping(target = "placeImage", source = "placeImage")
    @Mapping(target = "rate", source = "rate")
    BookmarkInfoDto bookmarkToBookmarkInfoDto(BookmarkDto  bookmarkDto);
}
