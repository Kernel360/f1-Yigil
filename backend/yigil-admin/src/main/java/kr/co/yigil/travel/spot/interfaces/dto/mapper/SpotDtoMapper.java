package kr.co.yigil.travel.spot.interfaces.dto.mapper;

import kr.co.yigil.travel.spot.domain.SpotInfoDto;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.AdminSpotDetailInfo;
import kr.co.yigil.travel.spot.interfaces.dto.SpotDto;
import kr.co.yigil.travel.spot.interfaces.dto.SpotDto.SpotListInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SpotDtoMapper {

    default SpotDto.AdminSpotsResponse of(SpotInfoDto.AdminSpotList spots){
        return new SpotDto.AdminSpotsResponse(mapToPage(spots.getSpots()));
    }

    SpotDto.SpotListInfo of(SpotInfoDto.SpotList spot);

    SpotDto.AdminSpotDetailResponse of(AdminSpotDetailInfo spot);

    default Page<SpotListInfo> mapToPage(Page<SpotInfoDto.SpotList> page) {
        return new PageImpl<>(page.getContent().stream()
            .map(this::of)
            .toList(),
            page.getPageable(),
            page.getTotalElements());
    }
}
