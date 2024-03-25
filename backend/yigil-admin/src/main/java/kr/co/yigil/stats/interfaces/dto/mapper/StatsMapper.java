package kr.co.yigil.stats.interfaces.dto.mapper;

import java.util.List;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.stats.interfaces.dto.response.RegionStatsResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface StatsMapper {

    default RegionStatsResponse toResponse(List<DailyRegion> dailyRegions) {
        return new RegionStatsResponse(dailyRegions.stream().map(this::toDto).toList());
    }

    @Mapping(target = "region", expression = "java(dailyRegion.getRegion().getName())")
    RegionStatsResponse.RegionStatsInfo toDto(DailyRegion dailyRegion);
}
