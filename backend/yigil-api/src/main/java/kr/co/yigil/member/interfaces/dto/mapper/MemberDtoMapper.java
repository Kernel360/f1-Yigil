package kr.co.yigil.member.interfaces.dto.mapper;


import kr.co.yigil.member.domain.MemberCommand;
import kr.co.yigil.member.domain.MemberCommand.VisibilityChangeRequest;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.domain.MemberInfo.VisibilityChangeResponse;
import kr.co.yigil.member.interfaces.dto.MemberDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoMapper {
    MemberCommand.MemberUpdateRequest of(MemberDto.MemberUpdateRequest request);
    VisibilityChangeRequest of(MemberDto.TravelsVisibilityRequest request);


    MemberDto.Main of(MemberInfo.Main main);
    MemberDto.MemberUpdateResponse of(MemberInfo.MemberUpdateResponse response);

    MemberDto.PlaceInfo of(MemberInfo.PlaceInfo placeInfo);
    MemberDto.FollowerResponse of(MemberInfo.FollowerResponse followerResponse);
    MemberDto.FollowingResponse of(MemberInfo.FollowingResponse followingResponse);
    MemberDto.FollowInfo of(MemberInfo.FollowInfo followInfo);

    MemberDto.MemberDeleteResponse of(MemberInfo.MemberDeleteResponse response);

    MemberDto.TravelsVisibilityResponse of(VisibilityChangeResponse response);

}
