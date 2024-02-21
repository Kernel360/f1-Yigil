package kr.co.yigil.travel.interfaces.dto.mapper;

import kr.co.yigil.travel.domain.spot.SpotCommand;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SpotDtoMapper {

    SpotDtoMapper INSTANCE = Mappers.getMapper(SpotDtoMapper.class);

    @Mappings({
            @Mapping(target = "registerPlaceRequest.mapStaticImageFile", source = "mapStaticImageFile"),
            @Mapping(target = "registerPlaceRequest.placeImageFile", source = "placeImageFile"),
            @Mapping(target = "registerPlaceRequest.placeName", source = "placeName"),
            @Mapping(target = "registerPlaceRequest.placeAddress", source = "placeAddress"),
            @Mapping(target = "registerPlaceRequest.placePointJson", source = "placePointJson"),
            @Mapping(target = "files", source = "files"),
            @Mapping(target = "pointJson", source = "pointJson"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "rate", source = "rate")
    })
    SpotCommand.RegisterSpotRequest toRegisterSpotRequest(SpotRegisterRequest request);

    default SpotCommand.RegisterPlaceRequest toRegisterPlaceRequest(SpotRegisterRequest request) {
        return SpotCommand.RegisterPlaceRequest.builder()
                .mapStaticImageFile(request.getMapStaticImageFile())
                .placeImageFile(request.getPlaceImageFile())
                .placeName(request.getPlaceName())
                .placeAddress(request.getPlaceAddress())
                .placePointJson(request.getPlacePointJson())
                .build();
    }
}

