package kr.co.yigil.member.interfaces.dto.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import kr.co.yigil.member.domain.MemberCommand;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.interfaces.dto.MemberDto;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoMapper {

    @Mapping(target="favoriteRegionIds", source="favoriteRegionIds")
    MemberCommand.MemberUpdateRequest of(MemberDto.MemberUpdateRequest request);

    MemberDto.Main of(MemberInfo.Main main);
    MemberDto.MemberUpdateResponse of(MemberInfo.MemberUpdateResponse response);
    MemberDto.MemberDeleteResponse of(MemberInfo.MemberDeleteResponse response);

    // @Mapping(target="available", source="available")
    MemberDto.NicknameCheckResponse of(MemberInfo.NicknameCheckInfo response);
}
