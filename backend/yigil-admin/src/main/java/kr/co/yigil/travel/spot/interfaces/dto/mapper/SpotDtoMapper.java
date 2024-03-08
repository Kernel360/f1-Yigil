package kr.co.yigil.travel.spot.interfaces.dto.mapper;

import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotDetailInfo;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotListUnit;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotPageInfo;
import kr.co.yigil.travel.spot.interfaces.dto.SpotDto;
import kr.co.yigil.travel.spot.interfaces.dto.SpotDto.SpotDetailResponse;
import kr.co.yigil.travel.spot.interfaces.dto.SpotDto.SpotListInfo;
import kr.co.yigil.travel.spot.interfaces.dto.SpotDto.SpotsResponse;
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

    default SpotsResponse of(SpotPageInfo spots){
        return new SpotsResponse(mapToPage(spots.getSpots()));
    }

    SpotDto.SpotListInfo of(SpotListUnit spot);

    SpotDetailResponse of(SpotDetailInfo spot);

    default Page<SpotListInfo> mapToPage(Page<SpotListUnit> page) {
        return new PageImpl<>(page.getContent().stream()
            .map(this::of)
            .toList(),
            page.getPageable(),
            page.getTotalElements());
    }
}
