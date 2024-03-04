package kr.co.yigil.favor.interfaces.dto.mapper;

import kr.co.yigil.favor.domain.FavorInfo;
import kr.co.yigil.favor.interfaces.dto.FavorDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FavorMapper {

    FavorDto.AddFavorResponse of(FavorInfo.AddFavorResponse response);
    FavorDto.DeleteFavorResponse of(FavorInfo.DeleteFavorResponse response);

}
