package kr.co.yigil.place.interfaces.dto.mapper;

import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.interfaces.dto.PlaceInfoDto;
import kr.co.yigil.place.interfaces.dto.response.PlaceDetailResponse;
import kr.co.yigil.place.interfaces.dto.response.PlacesResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
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

    PlaceInfoDto toDto(Place place);

    PlaceDetailResponse toResponse(Place place);
}
