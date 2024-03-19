package kr.co.yigil.place.interfaces.dto.mapper;

import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand;
import kr.co.yigil.place.interfaces.dto.request.PlaceImageUpdateRequest;
import kr.co.yigil.place.interfaces.dto.response.PlaceDetailResponse;
import kr.co.yigil.place.interfaces.dto.response.PlacesResponse;
import kr.co.yigil.place.interfaces.dto.response.PointDto;
import org.locationtech.jts.geom.Point;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PlaceMapper {

    default PlacesResponse toResponse(Page<Place> places) {
        return new PlacesResponse(places.map(this::toDto));
    }

    @Mapping(target = "location", source = "location")
    PlacesResponse.PlaceInfoDto toDto(Place place);

    default PointDto map(Point value){
        return new PointDto(value.getX(), value.getY());
    }


    @Mapping(target = "location", source = "location")
    PlaceDetailResponse toResponse(Place place);

    PlaceCommand.UpdateImageCommand toCommand(PlaceImageUpdateRequest request);
}
