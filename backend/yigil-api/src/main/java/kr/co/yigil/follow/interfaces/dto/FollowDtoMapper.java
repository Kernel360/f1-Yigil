package kr.co.yigil.follow.interfaces.dto;

import kr.co.yigil.follow.domain.FollowInfo;
import kr.co.yigil.follow.interfaces.dto.FollowDto.FollowersResponse;
import kr.co.yigil.follow.interfaces.dto.FollowDto.FollowingsResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FollowDtoMapper {
    FollowersResponse of (FollowInfo.FollowersResponse followersResponse);

    FollowingsResponse of (FollowInfo.FollowingsResponse followingsResponse);
}
