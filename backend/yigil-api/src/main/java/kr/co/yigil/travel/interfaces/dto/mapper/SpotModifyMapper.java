package kr.co.yigil.travel.interfaces.dto.mapper;

import kr.co.yigil.travel.domain.spot.SpotCommand;
import kr.co.yigil.travel.interfaces.dto.request.SpotUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SpotModifyMapper {

    SpotModifyMapper INSTANCE = Mappers.getMapper(SpotModifyMapper.class);

    @Mappings({
            @Mapping(target = "rate", source = "rate"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "originalImages", source = "originalSpotImages"),
            @Mapping(target = "updatedImages", source = "updateSpotImages")
    })
    SpotCommand.ModifySpotRequest toModifySpotRequest(SpotUpdateRequest request);
}
