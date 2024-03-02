package kr.co.yigil.member.interfaces.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.member.domain.MemberCommand;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.interfaces.dto.MemberDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-29T18:10:40+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class MemberDtoMapperImpl implements MemberDtoMapper {

    @Override
    public MemberCommand.MemberUpdateRequest of(MemberDto.MemberUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        MemberCommand.MemberUpdateRequest.MemberUpdateRequestBuilder memberUpdateRequest = MemberCommand.MemberUpdateRequest.builder();

        List<Long> list = request.getFavoriteRegionIds();
        if ( list != null ) {
            memberUpdateRequest.favoriteRegionIds( new ArrayList<Long>( list ) );
        }
        memberUpdateRequest.nickname( request.getNickname() );
        memberUpdateRequest.ages( request.getAges() );
        memberUpdateRequest.gender( request.getGender() );
        memberUpdateRequest.profileImageFile( request.getProfileImageFile() );

        return memberUpdateRequest.build();
    }

    @Override
    public MemberDto.Main of(MemberInfo.Main main) {
        if ( main == null ) {
            return null;
        }

        MemberDto.Main.MainBuilder main1 = MemberDto.Main.builder();

        main1.memberId( main.getMemberId() );
        main1.email( main.getEmail() );
        main1.nickname( main.getNickname() );
        main1.profileImageUrl( main.getProfileImageUrl() );
        List<Long> list = main.getFavoriteRegionIds();
        if ( list != null ) {
            main1.favoriteRegionIds( new ArrayList<Long>( list ) );
        }
        main1.followingCount( main.getFollowingCount() );
        main1.followerCount( main.getFollowerCount() );

        return main1.build();
    }

    @Override
    public MemberDto.MemberUpdateResponse of(MemberInfo.MemberUpdateResponse response) {
        if ( response == null ) {
            return null;
        }

        MemberDto.MemberUpdateResponse.MemberUpdateResponseBuilder memberUpdateResponse = MemberDto.MemberUpdateResponse.builder();

        memberUpdateResponse.message( response.getMessage() );

        return memberUpdateResponse.build();
    }

    @Override
    public MemberDto.MemberDeleteResponse of(MemberInfo.MemberDeleteResponse response) {
        if ( response == null ) {
            return null;
        }

        MemberDto.MemberDeleteResponse.MemberDeleteResponseBuilder memberDeleteResponse = MemberDto.MemberDeleteResponse.builder();

        memberDeleteResponse.message( response.getMessage() );

        return memberDeleteResponse.build();
    }
}
