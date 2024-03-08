package kr.co.yigil.member.interfaces.dto.mapper;

import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.interfaces.dto.response.MemberInfoDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-08T10:55:32+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    private final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168 = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );

    @Override
    public MemberInfoDto toDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberInfoDto.MemberInfoDtoBuilder memberInfoDto = MemberInfoDto.builder();

        memberInfoDto.memberId( member.getId() );
        memberInfoDto.nickname( member.getNickname() );
        memberInfoDto.profileImageUrl( member.getProfileImageUrl() );
        memberInfoDto.status( member.getStatus() );
        if ( member.getJoinedAt() != null ) {
            memberInfoDto.joinedAt( dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168.format( member.getJoinedAt() ) );
        }

        return memberInfoDto.build();
    }
}
