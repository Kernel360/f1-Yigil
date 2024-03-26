package kr.co.yigil.bookmark.interfaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.bookmark.domain.BookmarkInfo;
import kr.co.yigil.bookmark.interfaces.dto.BookmarkInfoDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-26T00:24:08+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class BookmarkMapperImpl implements BookmarkMapper {

    @Override
    public BookmarkInfoDto bookmarkToBookmarkInfoDto(BookmarkInfo bookmark) {
        if ( bookmark == null ) {
            return null;
        }

        BookmarkInfoDto bookmarkInfoDto = new BookmarkInfoDto();

        bookmarkInfoDto.setBookmarkId( bookmark.getBookmarkId() );
        bookmarkInfoDto.setPlaceId( bookmark.getPlaceId() );
        bookmarkInfoDto.setPlaceName( bookmark.getPlaceName() );
        bookmarkInfoDto.setPlaceImage( bookmark.getPlaceImage() );
        bookmarkInfoDto.setRate( bookmark.getRate() );

        return bookmarkInfoDto;
    }
}
