package kr.co.yigil.travel.interfaces.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.interfaces.dto.SpotInfoDto;
import kr.co.yigil.travel.interfaces.dto.response.SpotsInPlaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
}
