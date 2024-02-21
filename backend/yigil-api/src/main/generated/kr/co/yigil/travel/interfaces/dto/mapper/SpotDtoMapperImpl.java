package kr.co.yigil.travel.interfaces.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.travel.domain.spot.SpotCommand;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-21T10:58:38+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class SpotDtoMapperImpl implements SpotDtoMapper {

    @Override
    public SpotCommand.RegisterSpotRequest toRegisterSpotRequest(SpotRegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        SpotCommand.RegisterSpotRequest.RegisterSpotRequestBuilder registerSpotRequest = SpotCommand.RegisterSpotRequest.builder();

        registerSpotRequest.registerPlaceRequest( spotRegisterRequestToRegisterPlaceRequest( request ) );
        List<MultipartFile> list = request.getFiles();
        if ( list != null ) {
            registerSpotRequest.files( new ArrayList<MultipartFile>( list ) );
        }
        registerSpotRequest.pointJson( request.getPointJson() );
        registerSpotRequest.title( request.getTitle() );
        registerSpotRequest.description( request.getDescription() );
        registerSpotRequest.rate( request.getRate() );

        return registerSpotRequest.build();
    }

    protected SpotCommand.RegisterPlaceRequest spotRegisterRequestToRegisterPlaceRequest(SpotRegisterRequest spotRegisterRequest) {
        if ( spotRegisterRequest == null ) {
            return null;
        }

        SpotCommand.RegisterPlaceRequest.RegisterPlaceRequestBuilder registerPlaceRequest = SpotCommand.RegisterPlaceRequest.builder();

        registerPlaceRequest.mapStaticImageFile( spotRegisterRequest.getMapStaticImageFile() );
        registerPlaceRequest.placeImageFile( spotRegisterRequest.getPlaceImageFile() );
        registerPlaceRequest.placeName( spotRegisterRequest.getPlaceName() );
        registerPlaceRequest.placeAddress( spotRegisterRequest.getPlaceAddress() );
        registerPlaceRequest.placePointJson( spotRegisterRequest.getPlacePointJson() );

        return registerPlaceRequest.build();
    }
}
