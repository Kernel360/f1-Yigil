package kr.co.yigil.bookmark.interfaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.bookmark.domain.Bookmark;
import kr.co.yigil.bookmark.interfaces.dto.BookmarkInfoDto;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.place.domain.Place;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-11T15:27:23+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class BookmarkMapperImpl implements BookmarkMapper {

    @Override
    public BookmarkInfoDto bookmarkToBookmarkInfoDto(Bookmark bookmark) {
        if ( bookmark == null ) {
            return null;
        }

        BookmarkInfoDto bookmarkInfoDto = new BookmarkInfoDto();

        bookmarkInfoDto.setPlaceId( bookmarkPlaceId( bookmark ) );
        bookmarkInfoDto.setPlaceName( bookmarkPlaceName( bookmark ) );
        bookmarkInfoDto.setPlaceImage( bookmarkPlaceImageFileFileUrl( bookmark ) );

        bookmarkInfoDto.setRate( (double) 5.0 );

        return bookmarkInfoDto;
    }

    private Long bookmarkPlaceId(Bookmark bookmark) {
        if ( bookmark == null ) {
            return null;
        }
        Place place = bookmark.getPlace();
        if ( place == null ) {
            return null;
        }
        Long id = place.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String bookmarkPlaceName(Bookmark bookmark) {
        if ( bookmark == null ) {
            return null;
        }
        Place place = bookmark.getPlace();
        if ( place == null ) {
            return null;
        }
        String name = place.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String bookmarkPlaceImageFileFileUrl(Bookmark bookmark) {
        if ( bookmark == null ) {
            return null;
        }
        Place place = bookmark.getPlace();
        if ( place == null ) {
            return null;
        }
        AttachFile imageFile = place.getImageFile();
        if ( imageFile == null ) {
            return null;
        }
        String fileUrl = imageFile.getFileUrl();
        if ( fileUrl == null ) {
            return null;
        }
        return fileUrl;
    }
}
