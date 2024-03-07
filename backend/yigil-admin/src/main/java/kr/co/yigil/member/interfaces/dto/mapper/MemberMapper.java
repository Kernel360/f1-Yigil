package kr.co.yigil.member.interfaces.dto.mapper;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.interfaces.dto.response.MemberInfoDto;
import kr.co.yigil.member.interfaces.dto.response.MembersResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    default MembersResponse toResponse(Page<Member> memberPage) {
        Page<MemberInfoDto> memberInfoDtoPage = memberPage.map(this::toDto);
        return new MembersResponse(memberInfoDtoPage);
    }

    @Mapping(target = "memberId", source = "id")
    @Mapping(target = "nickname", source = "nickname")
    @Mapping(target = "profileImageUrl", source = "profileImageUrl")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "joinedAt", source = "joinedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    MemberInfoDto toDto(Member member);
}
