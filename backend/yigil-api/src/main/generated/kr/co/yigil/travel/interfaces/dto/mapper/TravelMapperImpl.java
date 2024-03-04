package kr.co.yigil.travel.interfaces.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.travel.domain.TravelCommand;
import kr.co.yigil.travel.interfaces.dto.request.TravelsVisibilityChangeRequest;
import kr.co.yigil.travel.interfaces.dto.response.TravelsVisibilityChangeResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-03T20:17:29+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class TravelMapperImpl implements TravelMapper {

    @Override
    public TravelCommand.VisibilityChangeRequest of(TravelsVisibilityChangeRequest request) {
        if ( request == null ) {
            return null;
        }

        TravelCommand.VisibilityChangeRequest.VisibilityChangeRequestBuilder visibilityChangeRequest = TravelCommand.VisibilityChangeRequest.builder();

        List<Long> list = request.getTravelIds();
        if ( list != null ) {
            visibilityChangeRequest.travelIds( new ArrayList<Long>( list ) );
        }
        visibilityChangeRequest.isPrivate( request.getIsPrivate() );

        return visibilityChangeRequest.build();
    }

    @Override
    public TravelsVisibilityChangeResponse of(String message) {
        if ( message == null ) {
            return null;
        }

        String message1 = null;

        message1 = message;

        TravelsVisibilityChangeResponse travelsVisibilityChangeResponse = new TravelsVisibilityChangeResponse( message1 );

        return travelsVisibilityChangeResponse;
    }
}
