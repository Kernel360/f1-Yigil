package kr.co.yigil.travel.spot.interfaces.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.travel.spot.domain.AdminSpotInfoDto;
import kr.co.yigil.travel.spot.interfaces.dto.AdminSpotDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-07T16:52:41+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class AdminSpotDtoMapperImpl implements AdminSpotDtoMapper {

    @Override
    public AdminSpotDto.SpotListInfo of(AdminSpotInfoDto.SpotList spot) {
        if ( spot == null ) {
            return null;
        }

        AdminSpotDto.SpotListInfo spotListInfo = new AdminSpotDto.SpotListInfo();

        spotListInfo.setSpotId( spot.getSpotId() );
        spotListInfo.setTitle( spot.getTitle() );
        spotListInfo.setCreatedAt( spot.getCreatedAt() );
        spotListInfo.setFavorCount( spot.getFavorCount() );
        spotListInfo.setCommentCount( spot.getCommentCount() );

        return spotListInfo;
    }

    @Override
    public AdminSpotDto.AdminSpotDetailResponse of(AdminSpotInfoDto.AdminSpotDetailInfo spot) {
        if ( spot == null ) {
            return null;
        }

        AdminSpotDto.AdminSpotDetailResponse adminSpotDetailResponse = new AdminSpotDto.AdminSpotDetailResponse();

        adminSpotDetailResponse.setSpotId( spot.getSpotId() );
        adminSpotDetailResponse.setTitle( spot.getTitle() );
        adminSpotDetailResponse.setContent( spot.getContent() );
        adminSpotDetailResponse.setPlaceName( spot.getPlaceName() );
        adminSpotDetailResponse.setMapStaticImageUrl( spot.getMapStaticImageUrl() );
        adminSpotDetailResponse.setCreatedAt( spot.getCreatedAt() );
        adminSpotDetailResponse.setRate( spot.getRate() );
        adminSpotDetailResponse.setFavorCount( spot.getFavorCount() );
        adminSpotDetailResponse.setCommentCount( spot.getCommentCount() );
        List<String> list = spot.getImageUrls();
        if ( list != null ) {
            adminSpotDetailResponse.setImageUrls( new ArrayList<String>( list ) );
        }
        adminSpotDetailResponse.setWriterId( spot.getWriterId() );
        adminSpotDetailResponse.setWriterName( spot.getWriterName() );

        return adminSpotDetailResponse;
    }
}
