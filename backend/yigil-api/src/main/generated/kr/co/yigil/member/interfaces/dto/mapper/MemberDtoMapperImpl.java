package kr.co.yigil.member.interfaces.dto.mapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.member.domain.MemberCommand;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.interfaces.dto.MemberDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-24T16:00:27+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class MemberDtoMapperImpl implements MemberDtoMapper {

    @Override
    public MemberCommand.MemberUpdateRequest of(MemberDto.MemberUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        MemberCommand.MemberUpdateRequest.MemberUpdateRequestBuilder memberUpdateRequest = MemberCommand.MemberUpdateRequest.builder();

        memberUpdateRequest.nickname( request.getNickname() );
        memberUpdateRequest.ages( request.getAges() );
        memberUpdateRequest.gender( request.getGender() );
        memberUpdateRequest.profileImageFile( request.getProfileImageFile() );

        return memberUpdateRequest.build();
    }

    @Override
    public MemberCommand.CoursesVisibilityRequest of(MemberDto.CoursesVisibilityRequest request) {
        if ( request == null ) {
            return null;
        }

        MemberCommand.CoursesVisibilityRequest.CoursesVisibilityRequestBuilder coursesVisibilityRequest = MemberCommand.CoursesVisibilityRequest.builder();

        List<Long> list = request.getCourseIds();
        if ( list != null ) {
            coursesVisibilityRequest.courseIds( new ArrayList<Long>( list ) );
        }
        coursesVisibilityRequest.isPrivate( request.getIsPrivate() );

        return coursesVisibilityRequest.build();
    }

    @Override
    public MemberCommand.SpotsVisibilityRequest of(MemberDto.SpotsVisibilityRequest request) {
        if ( request == null ) {
            return null;
        }

        MemberCommand.SpotsVisibilityRequest.SpotsVisibilityRequestBuilder spotsVisibilityRequest = MemberCommand.SpotsVisibilityRequest.builder();

        List<Long> list = request.getSpotIds();
        if ( list != null ) {
            spotsVisibilityRequest.spotIds( new ArrayList<Long>( list ) );
        }
        spotsVisibilityRequest.isPrivate( request.getIsPrivate() );

        return spotsVisibilityRequest.build();
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
    public MemberDto.MemberCourseResponse of(MemberInfo.MemberCourseResponse response) {
        if ( response == null ) {
            return null;
        }

        MemberDto.MemberCourseResponse.MemberCourseResponseBuilder memberCourseResponse = MemberDto.MemberCourseResponse.builder();

        memberCourseResponse.courseList( courseInfoListToCourseInfoList( response.getCourseList() ) );
        memberCourseResponse.totalPages( response.getTotalPages() );

        return memberCourseResponse.build();
    }

    @Override
    public MemberDto.MemberSpotResponse of(MemberInfo.MemberSpotResponse response) {
        if ( response == null ) {
            return null;
        }

        MemberDto.MemberSpotResponse.MemberSpotResponseBuilder memberSpotResponse = MemberDto.MemberSpotResponse.builder();

        memberSpotResponse.spotList( spotInfoListToSpotInfoList( response.getSpotList() ) );
        memberSpotResponse.totalPages( response.getTotalPages() );

        return memberSpotResponse.build();
    }

    @Override
    public MemberDto.SpotInfo of(MemberInfo.SpotInfo spotInfo) {
        if ( spotInfo == null ) {
            return null;
        }

        MemberDto.SpotInfo.SpotInfoBuilder spotInfo1 = MemberDto.SpotInfo.builder();

        spotInfo1.spotId( spotInfo.getSpotId() );
        spotInfo1.title( spotInfo.getTitle() );
        spotInfo1.description( spotInfo.getDescription() );
        spotInfo1.pointJson( spotInfo.getPointJson() );
        spotInfo1.rate( spotInfo.getRate() );
        spotInfo1.imageUrl( spotInfo.getImageUrl() );
        if ( spotInfo.getCreatedDate() != null ) {
            spotInfo1.createdDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( spotInfo.getCreatedDate() ) );
        }
        spotInfo1.placeInfo( of( spotInfo.getPlaceInfo() ) );
        spotInfo1.isPrivate( spotInfo.getIsPrivate() );

        return spotInfo1.build();
    }

    @Override
    public MemberDto.CourseInfo of(MemberInfo.CourseInfo courseInfo) {
        if ( courseInfo == null ) {
            return null;
        }

        MemberDto.CourseInfo.CourseInfoBuilder courseInfo1 = MemberDto.CourseInfo.builder();

        courseInfo1.courseId( courseInfo.getCourseId() );
        courseInfo1.title( courseInfo.getTitle() );
        courseInfo1.description( courseInfo.getDescription() );
        courseInfo1.rate( courseInfo.getRate() );
        courseInfo1.spotCount( courseInfo.getSpotCount() );
        if ( courseInfo.getCreatedDate() != null ) {
            courseInfo1.createdDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( courseInfo.getCreatedDate() ) );
        }
        courseInfo1.mapStaticImageUrl( courseInfo.getMapStaticImageUrl() );
        courseInfo1.isPrivate( courseInfo.getIsPrivate() );

        return courseInfo1.build();
    }

    @Override
    public MemberDto.PlaceInfo of(MemberInfo.PlaceInfo placeInfo) {
        if ( placeInfo == null ) {
            return null;
        }

        MemberDto.PlaceInfo.PlaceInfoBuilder placeInfo1 = MemberDto.PlaceInfo.builder();

        placeInfo1.placeName( placeInfo.getPlaceName() );
        placeInfo1.placeAddress( placeInfo.getPlaceAddress() );
        placeInfo1.mapStaticImageUrl( placeInfo.getMapStaticImageUrl() );
        placeInfo1.placeImageUrl( placeInfo.getPlaceImageUrl() );

        return placeInfo1.build();
    }

    @Override
    public MemberDto.FollowerResponse of(MemberInfo.FollowerResponse followerResponse) {
        if ( followerResponse == null ) {
            return null;
        }

        MemberDto.FollowerResponse.FollowerResponseBuilder followerResponse1 = MemberDto.FollowerResponse.builder();

        followerResponse1.followerList( followInfoListToFollowInfoList( followerResponse.getFollowerList() ) );
        followerResponse1.hasNext( followerResponse.isHasNext() );

        return followerResponse1.build();
    }

    @Override
    public MemberDto.FollowingResponse of(MemberInfo.FollowingResponse followingResponse) {
        if ( followingResponse == null ) {
            return null;
        }

        MemberDto.FollowingResponse.FollowingResponseBuilder followingResponse1 = MemberDto.FollowingResponse.builder();

        followingResponse1.followingList( followInfoListToFollowInfoList( followingResponse.getFollowingList() ) );
        followingResponse1.hasNext( followingResponse.isHasNext() );

        return followingResponse1.build();
    }

    @Override
    public MemberDto.FollowInfo of(MemberInfo.FollowInfo followInfo) {
        if ( followInfo == null ) {
            return null;
        }

        MemberDto.FollowInfo.FollowInfoBuilder followInfo1 = MemberDto.FollowInfo.builder();

        followInfo1.memberId( followInfo.getMemberId() );
        followInfo1.nickname( followInfo.getNickname() );
        followInfo1.profileImageUrl( followInfo.getProfileImageUrl() );

        return followInfo1.build();
    }

    @Override
    public MemberDto.CoursesVisibilityResponse of(MemberInfo.CoursesVisibilityResponse response) {
        if ( response == null ) {
            return null;
        }

        MemberDto.CoursesVisibilityResponse.CoursesVisibilityResponseBuilder coursesVisibilityResponse = MemberDto.CoursesVisibilityResponse.builder();

        coursesVisibilityResponse.message( response.getMessage() );

        return coursesVisibilityResponse.build();
    }

    protected List<MemberDto.CourseInfo> courseInfoListToCourseInfoList(List<MemberInfo.CourseInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<MemberDto.CourseInfo> list1 = new ArrayList<MemberDto.CourseInfo>( list.size() );
        for ( MemberInfo.CourseInfo courseInfo : list ) {
            list1.add( of( courseInfo ) );
        }

        return list1;
    }

    protected List<MemberDto.SpotInfo> spotInfoListToSpotInfoList(List<MemberInfo.SpotInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<MemberDto.SpotInfo> list1 = new ArrayList<MemberDto.SpotInfo>( list.size() );
        for ( MemberInfo.SpotInfo spotInfo : list ) {
            list1.add( of( spotInfo ) );
        }

        return list1;
    }

    protected List<MemberDto.FollowInfo> followInfoListToFollowInfoList(List<MemberInfo.FollowInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<MemberDto.FollowInfo> list1 = new ArrayList<MemberDto.FollowInfo>( list.size() );
        for ( MemberInfo.FollowInfo followInfo : list ) {
            list1.add( of( followInfo ) );
        }

        return list1;
    }
}
