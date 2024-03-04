package kr.co.yigil.follow.interfaces.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.follow.domain.FollowInfo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-03T20:17:29+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class FollowDtoMapperImpl implements FollowDtoMapper {

    @Override
    public FollowDto.FollowerResponse of(FollowInfo.FollowersResponse followersResponse) {
        if ( followersResponse == null ) {
            return null;
        }

        FollowDto.FollowerResponse.FollowerResponseBuilder followerResponse = FollowDto.FollowerResponse.builder();

        followerResponse.content( followBasicInfoListToFollowBasicInfoList( followersResponse.getContent() ) );
        followerResponse.hasNext( followersResponse.isHasNext() );

        return followerResponse.build();
    }

    @Override
    public FollowDto.FollowingResponse of(FollowInfo.FollowingsResponse followingsResponse) {
        if ( followingsResponse == null ) {
            return null;
        }

        FollowDto.FollowingResponse.FollowingResponseBuilder followingResponse = FollowDto.FollowingResponse.builder();

        followingResponse.content( followBasicInfoListToFollowBasicInfoList( followingsResponse.getContent() ) );
        followingResponse.hasNext( followingsResponse.isHasNext() );

        return followingResponse.build();
    }

    protected FollowDto.FollowBasicInfo followBasicInfoToFollowBasicInfo(FollowInfo.FollowBasicInfo followBasicInfo) {
        if ( followBasicInfo == null ) {
            return null;
        }

        FollowDto.FollowBasicInfo.FollowBasicInfoBuilder followBasicInfo1 = FollowDto.FollowBasicInfo.builder();

        followBasicInfo1.memberId( followBasicInfo.getMemberId() );
        followBasicInfo1.nickname( followBasicInfo.getNickname() );
        followBasicInfo1.profileImageUrl( followBasicInfo.getProfileImageUrl() );

        return followBasicInfo1.build();
    }

    protected List<FollowDto.FollowBasicInfo> followBasicInfoListToFollowBasicInfoList(List<FollowInfo.FollowBasicInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<FollowDto.FollowBasicInfo> list1 = new ArrayList<FollowDto.FollowBasicInfo>( list.size() );
        for ( FollowInfo.FollowBasicInfo followBasicInfo : list ) {
            list1.add( followBasicInfoToFollowBasicInfo( followBasicInfo ) );
        }

        return list1;
    }
}
