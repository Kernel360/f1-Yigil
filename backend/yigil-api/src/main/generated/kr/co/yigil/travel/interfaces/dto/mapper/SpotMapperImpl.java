package kr.co.yigil.travel.interfaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.interfaces.dto.SpotInfoDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-21T18:48:48+0900",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class SpotMapperImpl implements SpotMapper {

    @Override
    public SpotInfoDto spotToSpotInfoDto(Spot spot) {
        if ( spot == null ) {
            return null;
        }

        SpotInfoDto spotInfoDto = new SpotInfoDto();

        spotInfoDto.setImageUrlList( spot.getAttachFiles().getUrls() );
        spotInfoDto.setOwnerProfileImageUrl( spot.getMember().getProfileImageUrl() );
        spotInfoDto.setOwnerNickname( spot.getMember().getNickname() );
        spotInfoDto.setRate( String.valueOf(spot.getRate()) );
        spotInfoDto.setCreateDate( spot.getCreatedAt().toString() );

        return spotInfoDto;
    }
}
