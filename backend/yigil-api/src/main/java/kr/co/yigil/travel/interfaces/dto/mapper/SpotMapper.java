package kr.co.yigil.travel.interfaces.dto.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand;
import kr.co.yigil.travel.domain.spot.SpotInfo;
import kr.co.yigil.travel.interfaces.dto.SpotDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.SpotInfoDto;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.SpotsInPlaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface SpotMapper {

    SpotMapper INSTANCE = Mappers.getMapper(SpotMapper.class);

    @Mapping(target = "imageUrlList", expression = "java(spot.getAttachFiles().getUrls())")
    @Mapping(target = "ownerProfileImageUrl", expression = "java(spot.getMember().getProfileImageUrl())")
    @Mapping(target = "ownerNickname", expression = "java(spot.getMember().getNickname())")
    @Mapping(target = "rate", expression = "java(String.valueOf(spot.getRate()))")
    @Mapping(target = "createDate", expression = "java(spot.getCreatedAt().toString())")
    SpotInfoDto spotToSpotInfoDto(Spot spot);

    default List<SpotInfoDto> spotsToSpotInfoDtoList(List<Spot> spots) {
        return spots.stream()
                .map(this::spotToSpotInfoDto)
                .collect(Collectors.toList());
    }

    default SpotsInPlaceResponse spotsSliceToSpotInPlaceResponse(Slice<Spot> spotsSlice) {
        List<SpotInfoDto> spotInfoDtoList = spotsToSpotInfoDtoList(spotsSlice.getContent());
        boolean hasNext = spotsSlice.hasNext();
        return new SpotsInPlaceResponse(spotInfoDtoList, hasNext);
    }

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
