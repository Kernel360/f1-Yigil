package kr.co.yigil.statistics.interfaces.dto;

import kr.co.yigil.statistics.domain.StaticInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface StatisticsMapper {

    @Mapping(target = "dailyFavors", source = "dailyFavors")
    StatisticsDto.DailyFavorsResponse toDailyFavorsResponse(StaticInfo.DailyFavorsInfo info);

    StatisticsDto.DailyFavorDto toDailyFavorDto(StaticInfo.DailyTravelFavorDetail detail);
}
