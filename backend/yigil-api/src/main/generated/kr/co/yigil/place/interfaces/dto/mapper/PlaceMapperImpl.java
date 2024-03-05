package kr.co.yigil.place.interfaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.place.domain.PlaceInfo;
import kr.co.yigil.place.interfaces.dto.PlaceDetailInfoDto;
import kr.co.yigil.place.interfaces.dto.PlaceInfoDto;
import kr.co.yigil.place.interfaces.dto.response.PlaceStaticImageResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-05T14:42:06+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PlaceMapperImpl implements PlaceMapper {

    @Override
    public PlaceStaticImageResponse toPlaceStaticImageResponse(PlaceInfo.MapStaticImageInfo info) {
        if ( info == null ) {
            return null;
        }

        String mapStaticImageUrl = null;
        boolean exists = false;

        mapStaticImageUrl = info.getImageUrl();
        exists = info.isExists();

        PlaceStaticImageResponse placeStaticImageResponse = new PlaceStaticImageResponse( exists, mapStaticImageUrl );

        return placeStaticImageResponse;
    }

    @Override
    public PlaceInfoDto mainToDto(PlaceInfo.Main main) {
        if ( main == null ) {
            return null;
        }

        Long id = null;
        String placeName = null;
        String thumbnailImageUrl = null;
        boolean isBookmarked = false;

        id = main.getId();
        placeName = main.getName();
        thumbnailImageUrl = main.getThumbnailImageUrl();
        isBookmarked = main.isBookmarked();

        String reviewCount = String.valueOf(main.getReviewCount());
        String rate = String.format("%.1f", main.getRate());

        PlaceInfoDto placeInfoDto = new PlaceInfoDto( id, placeName, reviewCount, thumbnailImageUrl, rate, isBookmarked );

        placeInfoDto.setBookmarked( main.isBookmarked() );

        return placeInfoDto;
    }

    @Override
    public PlaceDetailInfoDto toPlaceDetailInfoDto(PlaceInfo.Detail detail) {
        if ( detail == null ) {
            return null;
        }

        Long id = null;
        String placeName = null;
        String address = null;
        String thumbnailImageUrl = null;
        String mapStaticImageUrl = null;
        boolean isBookmarked = false;
        double rate = 0.0d;
        int reviewCount = 0;

        id = detail.getId();
        placeName = detail.getName();
        address = detail.getAddress();
        thumbnailImageUrl = detail.getThumbnailImageUrl();
        mapStaticImageUrl = detail.getMapStaticImageUrl();
        isBookmarked = detail.isBookmarked();
        rate = detail.getRate();
        reviewCount = detail.getReviewCount();

        PlaceDetailInfoDto placeDetailInfoDto = new PlaceDetailInfoDto( id, placeName, address, thumbnailImageUrl, mapStaticImageUrl, isBookmarked, rate, reviewCount );

        placeDetailInfoDto.setBookmarked( detail.isBookmarked() );

        return placeDetailInfoDto;
    }
}
