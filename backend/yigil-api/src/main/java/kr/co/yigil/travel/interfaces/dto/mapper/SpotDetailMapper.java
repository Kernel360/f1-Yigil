package kr.co.yigil.travel.interfaces.dto.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import kr.co.yigil.travel.domain.spot.SpotInfo;
import kr.co.yigil.travel.interfaces.dto.SpotDetailInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SpotDetailMapper {

    SpotDetailMapper INSTANCE = Mappers.getMapper(SpotDetailMapper.class);

    @Mappings({
            @Mapping(source = "placeName", target = "placeName"),
            @Mapping(source = "rate", target = "rate", qualifiedByName = "doubleToString"),
            @Mapping(source = "placeAddress", target = "placeAddress"),
            @Mapping(source = "mapStaticImageFileUrl", target = "mapStaticImageFileUrl"),
            @Mapping(source = "imageUrls", target = "imageUrls"),
            @Mapping(source = "createDate", target = "createDate", qualifiedByName = "localDateTimeToString"),
            @Mapping(source = "description", target = "description")
    })
    SpotDetailInfoDto toSpotDetailInfoDto(SpotInfo.Main spotInfoMain);

    @Named("doubleToString")
    default String doubleToString(double rate) {
        return String.valueOf(rate);
    }

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime createDate) {
        return createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
