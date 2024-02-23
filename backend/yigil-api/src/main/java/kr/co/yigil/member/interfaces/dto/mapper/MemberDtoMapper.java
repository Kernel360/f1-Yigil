package kr.co.yigil.member.interfaces.dto.mapper;


import kr.co.yigil.member.domain.MemberCommand;
import kr.co.yigil.member.domain.MemberInfo;
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


    MemberDto.Main of(MemberInfo.Main main);
    MemberDto.MemberUpdateResponse of(MemberInfo.MemberUpdateResponse response);

    MemberDto.MemberCourseResponse of(MemberInfo.MemberCourseResponse response);
    MemberDto.MemberSpotResponse of(MemberInfo.MemberSpotResponse response);

    MemberDto.SpotInfo of(MemberInfo.SpotInfo spotInfo);
    MemberDto.CourseInfo of(MemberInfo.CourseInfo courseInfo);
    MemberDto.PlaceInfo of(MemberInfo.PlaceInfo placeInfo);

}
