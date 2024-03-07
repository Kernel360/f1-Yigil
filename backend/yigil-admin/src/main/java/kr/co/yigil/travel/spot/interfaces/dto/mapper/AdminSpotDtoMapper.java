package kr.co.yigil.travel.spot.interfaces.dto.mapper;

import kr.co.yigil.travel.spot.domain.AdminSpotInfoDto;
import kr.co.yigil.travel.spot.domain.AdminSpotInfoDto.AdminSpotDetailInfo;
import kr.co.yigil.travel.spot.interfaces.dto.AdminSpotDto;
import kr.co.yigil.travel.spot.interfaces.dto.AdminSpotDto.SpotListInfo;
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
public interface AdminSpotDtoMapper {

    default AdminSpotDto.AdminSpotsResponse of(AdminSpotInfoDto.AdminSpotList spots){
        return new AdminSpotDto.AdminSpotsResponse(mapToPage(spots.getSpots()));
    }

    AdminSpotDto.SpotListInfo of(AdminSpotInfoDto.SpotList spot);

    AdminSpotDto.AdminSpotDetailResponse of(AdminSpotDetailInfo spot);

    default Page<SpotListInfo> mapToPage(Page<AdminSpotInfoDto.SpotList> page) {
        return new PageImpl<>(page.getContent().stream()
            .map(this::of)
            .toList(),
            page.getPageable(),
            page.getTotalElements());
    }
}
