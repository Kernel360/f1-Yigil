package kr.co.yigil.member.interfaces.dto.mapper;


import kr.co.yigil.member.domain.MemberCommand;
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
    MemberCommand.TravelsVisibilityRequest of(MemberDto.TravelsVisibilityRequest request);


    MemberDto.Main of(MemberInfo.Main main);
    MemberDto.MemberUpdateResponse of(MemberInfo.MemberUpdateResponse response);

    MemberDto.CourseListResponse of(MemberInfo.CourseListResponse response);
    MemberDto.SpotListResponse of(MemberInfo.SpotListResponse response);

    MemberDto.SpotInfo of(MemberInfo.SpotInfo spotInfo);
    MemberDto.CourseInfo of(MemberInfo.CourseInfo courseInfo);

    MemberDto.PlaceInfo of(MemberInfo.PlaceInfo placeInfo);
    MemberDto.FollowerResponse of(MemberInfo.FollowerResponse followerResponse);
    MemberDto.FollowingResponse of(MemberInfo.FollowingResponse followingResponse);
    MemberDto.FollowInfo of(MemberInfo.FollowInfo followInfo);

    MemberDto.MemberDeleteResponse of(MemberInfo.MemberDeleteResponse response);

    MemberDto.TravelsVisibilityResponse of(VisibilityChangeResponse response);

}
