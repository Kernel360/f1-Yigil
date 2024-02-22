package kr.co.yigil.travel.interfaces.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.travel.domain.spot.SpotInfo;
import kr.co.yigil.travel.interfaces.dto.SpotDetailInfoDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-21T18:48:48+0900",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class SpotDetailMapperImpl implements SpotDetailMapper {

    @Override
    public SpotDetailInfoDto toSpotDetailInfoDto(SpotInfo.Main spotInfoMain) {
        if ( spotInfoMain == null ) {
            return null;
        }

        SpotDetailInfoDto spotDetailInfoDto = new SpotDetailInfoDto();

        spotDetailInfoDto.setPlaceName( spotInfoMain.getPlaceName() );
        spotDetailInfoDto.setRate( doubleToString( spotInfoMain.getRate() ) );
        spotDetailInfoDto.setPlaceAddress( spotInfoMain.getPlaceAddress() );
        spotDetailInfoDto.setMapStaticImageFileUrl( spotInfoMain.getMapStaticImageFileUrl() );
        List<String> list = spotInfoMain.getImageUrls();
        if ( list != null ) {
            spotDetailInfoDto.setImageUrls( new ArrayList<String>( list ) );
        }
        spotDetailInfoDto.setCreateDate( localDateTimeToString( spotInfoMain.getCreateDate() ) );
        spotDetailInfoDto.setDescription( spotInfoMain.getDescription() );

        return spotDetailInfoDto;
    }
}
