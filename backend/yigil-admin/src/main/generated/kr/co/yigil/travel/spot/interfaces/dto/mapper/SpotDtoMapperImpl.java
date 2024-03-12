package kr.co.yigil.travel.spot.interfaces.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.travel.spot.domain.SpotInfoDto;
import kr.co.yigil.travel.spot.interfaces.dto.SpotDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-08T10:55:32+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class SpotDtoMapperImpl implements SpotDtoMapper {

    @Override
    public SpotDto.SpotListInfo of(SpotInfoDto.SpotListUnit spot) {
        if ( spot == null ) {
            return null;
        }

        SpotDto.SpotListInfo spotListInfo = new SpotDto.SpotListInfo();

        spotListInfo.setSpotId( spot.getSpotId() );
        spotListInfo.setTitle( spot.getTitle() );
        spotListInfo.setCreatedAt( spot.getCreatedAt() );
        spotListInfo.setFavorCount( spot.getFavorCount() );
        spotListInfo.setCommentCount( spot.getCommentCount() );

        return spotListInfo;
    }

    @Override
    public SpotDto.SpotDetailResponse of(SpotInfoDto.SpotDetailInfo spot) {
        if ( spot == null ) {
            return null;
        }

        SpotDto.SpotDetailResponse spotDetailResponse = new SpotDto.SpotDetailResponse();

        spotDetailResponse.setSpotId( spot.getSpotId() );
        spotDetailResponse.setTitle( spot.getTitle() );
        spotDetailResponse.setContent( spot.getContent() );
        spotDetailResponse.setPlaceName( spot.getPlaceName() );
        spotDetailResponse.setMapStaticImageUrl( spot.getMapStaticImageUrl() );
        spotDetailResponse.setCreatedAt( spot.getCreatedAt() );
        spotDetailResponse.setRate( spot.getRate() );
        spotDetailResponse.setFavorCount( spot.getFavorCount() );
        spotDetailResponse.setCommentCount( spot.getCommentCount() );
        List<String> list = spot.getImageUrls();
        if ( list != null ) {
            spotDetailResponse.setImageUrls( new ArrayList<String>( list ) );
        }
        spotDetailResponse.setWriterId( spot.getWriterId() );
        spotDetailResponse.setWriterName( spot.getWriterName() );

        return spotDetailResponse;
    }
}
