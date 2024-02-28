package kr.co.yigil.follow.interfaces.dto;

import kr.co.yigil.follow.domain.FollowInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FollowDtoMapper {

    FollowDto.FollowerResponse of (FollowInfo.FollowersResponse followersResponse);

    FollowDto.FollowingResponse of (FollowInfo.FollowingsResponse followingsResponse);

}
