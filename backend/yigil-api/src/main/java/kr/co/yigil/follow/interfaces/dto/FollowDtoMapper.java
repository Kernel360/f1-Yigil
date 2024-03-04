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
//
//    default FollowersResponse of (FollowInfo.FollowersResponse followersResponse) {
//        return new FollowersResponse(
//                toFollowerInfoList(followersResponse.getContent()),
//                followersResponse.isHasNext());
//    }
//
//    default List<FollowerInfo> toFollowerInfoList(List<FollowInfo.FollowerInfo> followerInfoList) {
//        return followerInfoList.stream()
//                .map(this::toFollowerInfo)
//                .toList();
//    }
//
//    @Mapping(source = "memberId", target = "memberId")
//    @Mapping(source = "nickname", target = "nickname")
//    @Mapping(source = "profileImageUrl", target = "profileImageUrl")
//    @Mapping(source = "isFollowing", target = "isFollowing")
//    FollowDto.FollowerInfo toFollowerInfo(FollowInfo.FollowerInfo followerInfo);

    FollowersResponse of (FollowInfo.FollowersResponse followersResponse);

    FollowingsResponse of (FollowInfo.FollowingsResponse followingsResponse);
}
