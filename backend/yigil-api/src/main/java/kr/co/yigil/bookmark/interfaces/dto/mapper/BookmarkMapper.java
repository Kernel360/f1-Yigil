package kr.co.yigil.bookmark.interfaces.dto.mapper;

import java.util.List;
import kr.co.yigil.bookmark.domain.Bookmark;
import kr.co.yigil.bookmark.interfaces.dto.BookmarkInfoDto;
import kr.co.yigil.bookmark.interfaces.dto.response.BookmarksResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    default BookmarksResponse bookmarkSliceToBookmarksResponse(Slice<Bookmark> bookmarkSlice) {
        List<BookmarkInfoDto> bookmarkInfoDtoList = bookmarksToBookmarkInfoDtoList(
                bookmarkSlice.getContent());
        boolean hasNext = bookmarkSlice.hasNext();
        return new BookmarksResponse(bookmarkInfoDtoList, hasNext);
    }

    default List<BookmarkInfoDto> bookmarksToBookmarkInfoDtoList(List<Bookmark> bookmarks) {
        return bookmarks.stream()
                .map(this::bookmarkToBookmarkInfoDto)
                .toList();
    }

    @Mapping(target = "placeId", source = "place.id")
    @Mapping(target = "placeName", source = "place.name")
    @Mapping(target = "placeImage", source = "place.imageFile.fileUrl")
    @Mapping(target = "rate", constant = "5.0") // todo add rate
    BookmarkInfoDto bookmarkToBookmarkInfoDto(Bookmark bookmark);
}
