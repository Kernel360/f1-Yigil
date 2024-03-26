package kr.co.yigil.travel.interfaces.dto.mapper;

import kr.co.yigil.travel.domain.spot.SpotCommand;
import kr.co.yigil.travel.domain.spot.SpotInfo;
import kr.co.yigil.travel.domain.spot.SpotInfo.Main;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpotsResponse;
import kr.co.yigil.travel.interfaces.dto.SpotDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.SpotInfoDto;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.MySpotInPlaceResponse;
import kr.co.yigil.travel.interfaces.dto.response.MySpotsResponseDto;
import kr.co.yigil.travel.interfaces.dto.response.SpotsInPlaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SpotMapper {

    SpotMapper INSTANCE = Mappers.getMapper(SpotMapper.class);


    @Mappings({
            @Mapping(target = "exists", source = "mySpot.exists"),
            @Mapping(target = "rate", source = "mySpot.rate", qualifiedByName = "doubleToString"),
            @Mapping(target = "imageUrls", source = "mySpot.imageUrls"),
            @Mapping(target = "createDate", source = "mySpot.createDate", qualifiedByName = "localDateTimeToString"),
            @Mapping(target = "description", source = "mySpot.description")
    })
    MySpotInPlaceResponse toMySpotInPlaceResponse(SpotInfo.MySpot mySpot);

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

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "rate", source = "rate"),
        @Mapping(target = "description", source = "description"),
        @Mapping(target = "originalImages", source = "originalSpotImages"),
        @Mapping(target = "updatedImages", source = "updateSpotImages")
    })
    SpotCommand.ModifySpotRequest toModifySpotRequest(SpotUpdateRequest request);

    @Mappings({
        @Mapping(target = "registerPlaceRequest.mapStaticImageFile", source = "mapStaticImageFile"),
        @Mapping(target = "registerPlaceRequest.placeName", source = "placeName"),
        @Mapping(target = "registerPlaceRequest.placeAddress", source = "placeAddress"),
        @Mapping(target = "registerPlaceRequest.placePointJson", source = "pointJson"),
        @Mapping(target = "files", source = "files"),
        @Mapping(target = "pointJson", source = "pointJson"),
        @Mapping(target = "description", source = "description"),
        @Mapping(target = "rate", source = "rate")
    })
    SpotCommand.RegisterSpotRequest toRegisterSpotRequest(SpotRegisterRequest request);

    default SpotCommand.RegisterPlaceRequest toRegisterPlaceRequest(SpotRegisterRequest request) {
        return SpotCommand.RegisterPlaceRequest.builder()
            .mapStaticImageFile(request.getMapStaticImageFile())
            .placeName(request.getPlaceName())
            .placeAddress(request.getPlaceAddress())
            .placePointJson(request.getPointJson())
            .build();
    }

    MySpotsResponseDto of(MySpotsResponse mySpotsResponse);
    MySpotsResponseDto.SpotInfo of(SpotInfo.SpotListInfo spotInfo);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "imageUrls", target = "imageUrlList"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "ownerProfileImageUrl", target = "ownerProfileImageUrl"),
            @Mapping(source = "ownerNickname", target = "ownerNickname"),
            @Mapping(source = "rate", target = "rate", numberFormat = "#.#"),
            @Mapping(source = "createDate", target = "createDate", dateFormat = "yyyy-MM-dd"),
            @Mapping(source = "liked", target = "liked")
    })
    SpotInfoDto toSpotInfoDto(SpotInfo.Main spotInfoMain);

    default List<SpotInfoDto> spotsSliceToSpotInPlaceResponse(List<Main> mains) {
        return mains.stream()
                .map(this::toSpotInfoDto)
                .collect(Collectors.toList());
    }

    default SpotsInPlaceResponse toSpotsInPlaceResponse(SpotInfo.Slice slice) {
        List<SpotInfoDto> dtos = spotsSliceToSpotInPlaceResponse(slice.getMains());
        return new SpotsInPlaceResponse(dtos, slice.isHasNext());
        }
}
