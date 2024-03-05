package kr.co.yigil.place.interfaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand;
import kr.co.yigil.place.domain.PlaceInfo;
import kr.co.yigil.place.interfaces.dto.PlaceCoordinateDto;
import kr.co.yigil.place.interfaces.dto.PlaceDetailInfoDto;
import kr.co.yigil.place.interfaces.dto.PlaceInfoDto;
import kr.co.yigil.place.interfaces.dto.request.NearPlaceRequest;
import kr.co.yigil.place.interfaces.dto.response.PlaceStaticImageResponse;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-05T14:44:11+0900",
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

    @Override
    public PlaceCommand.NearPlaceRequest toNearPlaceCommand(NearPlaceRequest nearPlaceRequest) {
        if ( nearPlaceRequest == null ) {
            return null;
        }

        PlaceCommand.NearPlaceRequest.NearPlaceRequestBuilder nearPlaceRequest1 = PlaceCommand.NearPlaceRequest.builder();

        nearPlaceRequest1.minCoordinate( nearPlaceRequestToCoordinate( nearPlaceRequest ) );
        nearPlaceRequest1.maxCoordinate( nearPlaceRequestToCoordinate1( nearPlaceRequest ) );
        nearPlaceRequest1.pageNo( nearPlaceRequest.getPage() );

        return nearPlaceRequest1.build();
    }

    @Override
    public PlaceCoordinateDto placeToPlaceCoordinateDto(Place place) {
        if ( place == null ) {
            return null;
        }

        Long id = null;
        String placeName = null;
        double x = 0.0d;
        double y = 0.0d;

        id = place.getId();
        placeName = place.getName();
        x = placeLocationX( place );
        y = placeLocationY( place );

        PlaceCoordinateDto placeCoordinateDto = new PlaceCoordinateDto( id, x, y, placeName );

        return placeCoordinateDto;
    }

    protected PlaceCommand.Coordinate nearPlaceRequestToCoordinate(NearPlaceRequest nearPlaceRequest) {
        if ( nearPlaceRequest == null ) {
            return null;
        }

        PlaceCommand.Coordinate.CoordinateBuilder coordinate = PlaceCommand.Coordinate.builder();

        coordinate.x( nearPlaceRequest.getMinX() );
        coordinate.y( nearPlaceRequest.getMinY() );

        return coordinate.build();
    }

    protected PlaceCommand.Coordinate nearPlaceRequestToCoordinate1(NearPlaceRequest nearPlaceRequest) {
        if ( nearPlaceRequest == null ) {
            return null;
        }

        PlaceCommand.Coordinate.CoordinateBuilder coordinate = PlaceCommand.Coordinate.builder();

        coordinate.x( nearPlaceRequest.getMaxX() );
        coordinate.y( nearPlaceRequest.getMaxY() );

        return coordinate.build();
    }

    private double placeLocationX(Place place) {
        if ( place == null ) {
            return 0.0d;
        }
        Point location = place.getLocation();
        if ( location == null ) {
            return 0.0d;
        }
        double x = location.getX();
        return x;
    }

    private double placeLocationY(Place place) {
        if ( place == null ) {
            return 0.0d;
        }
        Point location = place.getLocation();
        if ( location == null ) {
            return 0.0d;
        }
        double y = location.getY();
        return y;
    }
}
