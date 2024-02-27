package kr.co.yigil.place.interfaces.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.place.domain.PlaceInfo;
import kr.co.yigil.place.domain.PlaceInfo.Main;
import kr.co.yigil.place.domain.PlaceInfo.MapStaticImageInfo;
import kr.co.yigil.place.interfaces.dto.PlaceInfoDto;
import kr.co.yigil.place.interfaces.dto.response.PlaceStaticImageResponse;
import kr.co.yigil.place.interfaces.dto.response.PopularPlaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    PlaceMapper INSTANCE = Mappers.getMapper(PlaceMapper.class);

    @Mappings({
            @Mapping(target = "mapStaticImageUrl", source = "imageUrl"),
            @Mapping(target = "exists", source = "exists")
    })
    PlaceStaticImageResponse toPlaceStaticImageResponse(MapStaticImageInfo info);


    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "placeName", source = "name"),
            @Mapping(target = "reviewCount", expression = "java(String.valueOf(main.getReviewCount()))"),
            @Mapping(target = "thumbnailImageUrl", source = "thumbnailImageUrl"),
            @Mapping(target = "rate", expression = "java(String.format(\"%.1f\", main.getRate()))"),
            @Mapping(target = "isBookmarked", source = "bookmarked")
    })
    PlaceInfoDto mainToDto(PlaceInfo.Main main);

    default PopularPlaceResponse toPopularPlaceResponse(List<Main> mains) {
        List<PlaceInfoDto> dtos = mains.stream().map(this::mainToDto).collect(Collectors.toList());
        return new PopularPlaceResponse(dtos);
    }
}
