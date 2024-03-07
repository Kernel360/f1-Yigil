package kr.co.yigil.place.interfaces.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand;
import kr.co.yigil.place.domain.PlaceInfo;
import kr.co.yigil.place.domain.PlaceInfo.Detail;
import kr.co.yigil.place.domain.PlaceInfo.Main;
import kr.co.yigil.place.domain.PlaceInfo.MapStaticImageInfo;
import kr.co.yigil.place.interfaces.dto.PlaceCoordinateDto;
import kr.co.yigil.place.interfaces.dto.PlaceDetailInfoDto;
import kr.co.yigil.place.interfaces.dto.PlaceInfoDto;
import kr.co.yigil.place.interfaces.dto.request.NearPlaceRequest;
import kr.co.yigil.place.interfaces.dto.response.NearPlaceResponse;
import kr.co.yigil.place.interfaces.dto.response.PlaceKeywordResponse;
import kr.co.yigil.place.interfaces.dto.response.PlaceStaticImageResponse;
import kr.co.yigil.place.interfaces.dto.response.PopularPlaceResponse;
import kr.co.yigil.place.interfaces.dto.response.RegionPlaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

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

    default RegionPlaceResponse toRegionPlaceResponse(List<Main> mains) {
        List<PlaceInfoDto> dtos = mains.stream().map(this::mainToDto).collect(Collectors.toList());
        return new RegionPlaceResponse(dtos);
    }

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "placeName", source = "name"),
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "thumbnailImageUrl", source = "thumbnailImageUrl"),
            @Mapping(target = "mapStaticImageUrl", source = "mapStaticImageUrl"),
            @Mapping(target = "isBookmarked", source = "bookmarked"),
            @Mapping(target = "rate", source = "rate"),
            @Mapping(target = "reviewCount", source = "reviewCount")
    })
    PlaceDetailInfoDto toPlaceDetailInfoDto(Detail detail);

    @Mapping(target = "minCoordinate.x", source = "minX")
    @Mapping(target = "minCoordinate.y", source = "minY")
    @Mapping(target = "maxCoordinate.x", source = "maxX")
    @Mapping(target = "maxCoordinate.y", source = "maxY")
    @Mapping(target = "pageNo", source = "page")
    PlaceCommand.NearPlaceRequest toNearPlaceCommand(NearPlaceRequest nearPlaceRequest);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "placeName", source = "name"),
            @Mapping(target = "x", source = "location.x"),
            @Mapping(target = "y", source = "location.y")
    })
    PlaceCoordinateDto placeToPlaceCoordinateDto(Place place);

    default NearPlaceResponse toNearPlaceResponse(Page<Place> page) {
        List<PlaceCoordinateDto> placeCoordinateDtos = page.getContent().stream()
                .map(this::placeToPlaceCoordinateDto)
                .toList();

        return new NearPlaceResponse(placeCoordinateDtos, page.getNumber() + 1, page.getTotalPages());
    }

    default PlaceKeywordResponse toPlaceKeywordResponse(List<PlaceInfo.Keyword> keywords) {
        List<String> keywordStrings = keywords.stream()
                .map(PlaceInfo.Keyword::getKeyword)
                .collect(Collectors.toList());

        return new PlaceKeywordResponse(keywordStrings);
    }
}
