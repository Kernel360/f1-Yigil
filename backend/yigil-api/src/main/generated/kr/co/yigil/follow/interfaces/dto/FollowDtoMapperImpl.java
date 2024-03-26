package kr.co.yigil.follow.interfaces.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.follow.domain.FollowInfo;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-26T00:24:08+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class FollowDtoMapperImpl implements FollowDtoMapper {

    @Override
    public FollowDto.FollowersResponse of(FollowInfo.FollowersResponse followersResponse) {
        if ( followersResponse == null ) {
            return null;
        }

        FollowDto.FollowersResponse.FollowersResponseBuilder followersResponse1 = FollowDto.FollowersResponse.builder();

        followersResponse1.content( followerInfoListToFollowerInfoList( followersResponse.getContent() ) );
        followersResponse1.hasNext( followersResponse.isHasNext() );

        return followersResponse1.build();
    }

    @Override
    public FollowDto.FollowingsResponse of(FollowInfo.FollowingsResponse followingsResponse) {
        if ( followingsResponse == null ) {
            return null;
        }

        FollowDto.FollowingsResponse.FollowingsResponseBuilder followingsResponse1 = FollowDto.FollowingsResponse.builder();

        followingsResponse1.content( followingInfoListToFollowingInfoList( followingsResponse.getContent() ) );
        followingsResponse1.hasNext( followingsResponse.isHasNext() );

        return followingsResponse1.build();
    }

    protected FollowDto.FollowerInfo followerInfoToFollowerInfo(FollowInfo.FollowerInfo followerInfo) {
        if ( followerInfo == null ) {
            return null;
        }

        FollowDto.FollowerInfo.FollowerInfoBuilder followerInfo1 = FollowDto.FollowerInfo.builder();

        followerInfo1.memberId( followerInfo.getMemberId() );
        followerInfo1.nickname( followerInfo.getNickname() );
        followerInfo1.profileImageUrl( followerInfo.getProfileImageUrl() );
        followerInfo1.following( followerInfo.isFollowing() );

        return followerInfo1.build();
    }

    protected List<FollowDto.FollowerInfo> followerInfoListToFollowerInfoList(List<FollowInfo.FollowerInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<FollowDto.FollowerInfo> list1 = new ArrayList<FollowDto.FollowerInfo>( list.size() );
        for ( FollowInfo.FollowerInfo followerInfo : list ) {
            list1.add( followerInfoToFollowerInfo( followerInfo ) );
        }

        return list1;
    }

    protected FollowDto.FollowingInfo followingInfoToFollowingInfo(FollowInfo.FollowingInfo followingInfo) {
        if ( followingInfo == null ) {
            return null;
        }

        FollowDto.FollowingInfo.FollowingInfoBuilder followingInfo1 = FollowDto.FollowingInfo.builder();

        followingInfo1.memberId( followingInfo.getMemberId() );
        followingInfo1.nickname( followingInfo.getNickname() );
        followingInfo1.profileImageUrl( followingInfo.getProfileImageUrl() );

        return followingInfo1.build();
    }

    protected List<FollowDto.FollowingInfo> followingInfoListToFollowingInfoList(List<FollowInfo.FollowingInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<FollowDto.FollowingInfo> list1 = new ArrayList<FollowDto.FollowingInfo>( list.size() );
        for ( FollowInfo.FollowingInfo followingInfo : list ) {
            list1.add( followingInfoToFollowingInfo( followingInfo ) );
        }

        return list1;
    }
}
