package kr.co.yigil.stats.interfaces.dto.mapper;

import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.stats.domain.StaticInfo;
import kr.co.yigil.stats.interfaces.dto.StatisticsDto;
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

    default StatisticsDto.RegionStatsResponse toResponse(List<DailyRegion> dailyRegions) {
        return new StatisticsDto.RegionStatsResponse(dailyRegions.stream().map(this::toDto).toList());
    }

    @Mapping(target = "region", expression = "java(dailyRegion.getRegion().getName())")
    StatisticsDto.RegionStatsInfo toDto(DailyRegion dailyRegion);

    @Mapping(target = "dailyFavors", source = "dailyFavors")
    StatisticsDto.DailyFavorsResponse toDailyFavorsResponse(StaticInfo.DailyFavorsInfo info);

    StatisticsDto.DailyFavorDto toDailyFavorDto(StaticInfo.DailyTravelFavorDetail detail);
}
