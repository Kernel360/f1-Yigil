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
    date = "2024-02-23T10:24:52+0900",
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
        memberCourseResponse.pageInfo( pageInfoToPageInfo( response.getPageInfo() ) );

        return memberCourseResponse.build();
    }

    @Override
    public MemberDto.MemberSpotResponse of(MemberInfo.MemberSpotResponse response) {
        if ( response == null ) {
            return null;
        }

        MemberDto.MemberSpotResponse.MemberSpotResponseBuilder memberSpotResponse = MemberDto.MemberSpotResponse.builder();

        memberSpotResponse.spotList( spotInfoListToSpotInfoList( response.getSpotList() ) );
        memberSpotResponse.pageInfo( pageInfoToPageInfo( response.getPageInfo() ) );

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
        List<String> list = spotInfo.getImageUrlList();
        if ( list != null ) {
            spotInfo1.imageUrlList( new ArrayList<String>( list ) );
        }
        if ( spotInfo.getCreatedDate() != null ) {
            spotInfo1.createdDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( spotInfo.getCreatedDate() ) );
        }
        spotInfo1.placeInfo( of( spotInfo.getPlaceInfo() ) );

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
        courseInfo1.lineStringJson( courseInfo.getLineStringJson() );
        courseInfo1.rate( courseInfo.getRate() );
        courseInfo1.spotCount( courseInfo.getSpotCount() );
        if ( courseInfo.getCreatedDate() != null ) {
            courseInfo1.createdDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( courseInfo.getCreatedDate() ) );
        }

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

    protected MemberDto.PageInfo pageInfoToPageInfo(MemberInfo.PageInfo pageInfo) {
        if ( pageInfo == null ) {
            return null;
        }

        MemberDto.PageInfo.PageInfoBuilder pageInfo1 = MemberDto.PageInfo.builder();

        pageInfo1.totalPages( pageInfo.getTotalPages() );

        return pageInfo1.build();
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
}
