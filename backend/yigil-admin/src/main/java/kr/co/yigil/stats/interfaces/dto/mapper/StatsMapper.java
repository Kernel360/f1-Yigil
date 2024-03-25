package kr.co.yigil.stats.interfaces.dto.mapper;

import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.stats.domain.StaticInfo;
import kr.co.yigil.stats.interfaces.dto.StatsDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface StatsMapper {

    default StatsDto.RegionStatsResponse toResponse(List<DailyRegion> dailyRegions) {
        return new StatsDto.RegionStatsResponse(dailyRegions.stream().map(this::toDto).toList());
    }

    @Mapping(target = "region", expression = "java(dailyRegion.getRegion().getName())")
    StatsDto.RegionStatsInfo toDto(DailyRegion dailyRegion);

    @Mapping(target = "dailyFavors", source = "dailyFavors")
    StatsDto.DailyTravelFavorsResponse toDailyFavorsResponse(StaticInfo.DailyTravelsFavorCountInfo info);

    StatsDto.TravelFavorDto toDailyTravelFavorDto(StaticInfo.DailyTravelFavorDetail detail);

    StatsDto.DailyTotalFavorCountResponse toDailyTotalFavorCountDto(StaticInfo.DailyTotalFavorCountInfo info);

    StatsDto.DailyTotalFavorCountDto toDailyFavorDto(StaticInfo.FavorTotalCountInfo detail);
}
