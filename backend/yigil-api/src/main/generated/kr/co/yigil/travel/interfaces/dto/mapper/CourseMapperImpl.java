package kr.co.yigil.travel.interfaces.dto.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.travel.domain.course.CourseCommand;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.domain.spot.SpotCommand;
import kr.co.yigil.travel.interfaces.dto.CourseDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.CourseDto;
import kr.co.yigil.travel.interfaces.dto.CourseInfoDto;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterWithoutSeriesRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.CourseSearchResponse;
import kr.co.yigil.travel.interfaces.dto.response.CoursesInPlaceResponse;
import kr.co.yigil.travel.interfaces.dto.response.MyCoursesResponse;
import kr.co.yigil.travel.interfaces.dto.response.MySpotsDetailResponse;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-26T00:24:08+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    private final SpotMapper spotMapper;

    @Autowired
    public CourseMapperImpl(SpotMapper spotMapper) {

        this.spotMapper = spotMapper;
    }

    @Override
    public CoursesInPlaceResponse courseSliceToCourseInPlaceResponse(CourseInfo.CoursesInPlaceResponseInfo courseSlice) {
        if ( courseSlice == null ) {
            return null;
        }

        CoursesInPlaceResponse coursesInPlaceResponse = new CoursesInPlaceResponse();

        coursesInPlaceResponse.setCourses( courseInPlaceInfoListToCourseInfoDtoList( courseSlice.getCourses() ) );
        coursesInPlaceResponse.setHasNext( courseSlice.isHasNext() );

        return coursesInPlaceResponse;
    }

    @Override
    public CourseInfoDto toDto(CourseInfo.CourseInPlaceInfo courseInPlaceInfo) {
        if ( courseInPlaceInfo == null ) {
            return null;
        }

        CourseInfoDto courseInfoDto = new CourseInfoDto();

        courseInfoDto.setId( courseInPlaceInfo.getId() );
        courseInfoDto.setTitle( courseInPlaceInfo.getTitle() );
        courseInfoDto.setContent( courseInPlaceInfo.getContent() );
        courseInfoDto.setMapStaticImageUrl( courseInPlaceInfo.getMapStaticImageUrl() );
        courseInfoDto.setRate( courseInPlaceInfo.getRate() );
        courseInfoDto.setSpotCount( courseInPlaceInfo.getSpotCount() );
        courseInfoDto.setCreateDate( courseInPlaceInfo.getCreateDate() );
        courseInfoDto.setOwnerId( courseInPlaceInfo.getOwnerId() );
        courseInfoDto.setOwnerProfileImageUrl( courseInPlaceInfo.getOwnerProfileImageUrl() );
        courseInfoDto.setOwnerNickname( courseInPlaceInfo.getOwnerNickname() );
        courseInfoDto.setLiked( courseInPlaceInfo.isLiked() );

        return courseInfoDto;
    }

    @Override
    public CourseCommand.RegisterCourseRequest toRegisterCourseRequest(CourseRegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        CourseCommand.RegisterCourseRequest.RegisterCourseRequestBuilder registerCourseRequest = CourseCommand.RegisterCourseRequest.builder();

        registerCourseRequest.title( request.getTitle() );
        registerCourseRequest.description( request.getDescription() );
        registerCourseRequest.rate( request.getRate() );
        registerCourseRequest.isPrivate( request.isPrivate() );
        registerCourseRequest.representativeSpotOrder( request.getRepresentativeSpotOrder() );
        registerCourseRequest.lineStringJson( request.getLineStringJson() );
        registerCourseRequest.mapStaticImageFile( request.getMapStaticImageFile() );
        registerCourseRequest.registerSpotRequests( spotRegisterRequestListToRegisterSpotRequestList( request.getSpotRegisterRequests() ) );

        return registerCourseRequest.build();
    }

    @Override
    public CourseCommand.RegisterCourseRequestWithSpotInfo toRegisterCourseRequest(CourseRegisterWithoutSeriesRequest request) {
        if ( request == null ) {
            return null;
        }

        CourseCommand.RegisterCourseRequestWithSpotInfo.RegisterCourseRequestWithSpotInfoBuilder registerCourseRequestWithSpotInfo = CourseCommand.RegisterCourseRequestWithSpotInfo.builder();

        registerCourseRequestWithSpotInfo.title( request.getTitle() );
        registerCourseRequestWithSpotInfo.description( request.getDescription() );
        registerCourseRequestWithSpotInfo.rate( request.getRate() );
        registerCourseRequestWithSpotInfo.isPrivate( request.isPrivate() );
        registerCourseRequestWithSpotInfo.representativeSpotOrder( request.getRepresentativeSpotOrder() );
        registerCourseRequestWithSpotInfo.lineStringJson( request.getLineStringJson() );
        registerCourseRequestWithSpotInfo.mapStaticImageFile( request.getMapStaticImageFile() );
        List<Long> list = request.getSpotIds();
        if ( list != null ) {
            registerCourseRequestWithSpotInfo.spotIds( new ArrayList<Long>( list ) );
        }

        return registerCourseRequestWithSpotInfo.build();
    }

    @Override
    public CourseCommand.ModifyCourseRequest toModifyCourseRequest(CourseUpdateRequest courseUpdateRequest) {
        if ( courseUpdateRequest == null ) {
            return null;
        }

        CourseCommand.ModifyCourseRequest.ModifyCourseRequestBuilder modifyCourseRequest = CourseCommand.ModifyCourseRequest.builder();

        List<Long> list = courseUpdateRequest.getSpotIdOrder();
        if ( list != null ) {
            modifyCourseRequest.spotIdOrder( new ArrayList<Long>( list ) );
        }
        modifyCourseRequest.modifySpotRequests( spotUpdateRequestListToModifySpotRequestList( courseUpdateRequest.getCourseSpotUpdateRequests() ) );
        modifyCourseRequest.description( courseUpdateRequest.getDescription() );
        modifyCourseRequest.rate( courseUpdateRequest.getRate() );
        modifyCourseRequest.title( courseUpdateRequest.getTitle() );
        try {
            modifyCourseRequest.lineStringJson( map( courseUpdateRequest.getLineStringJson() ) );
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        modifyCourseRequest.mapStaticImage( courseUpdateRequest.getMapStaticImage() );

        return modifyCourseRequest.build();
    }

    @Override
    public CourseDetailInfoDto toCourseDetailInfoDto(CourseInfo.Main courseInfo) {
        if ( courseInfo == null ) {
            return null;
        }

        CourseDetailInfoDto courseDetailInfoDto = new CourseDetailInfoDto();

        courseDetailInfoDto.setTitle( courseInfo.getTitle() );
        courseDetailInfoDto.setRate( Double.parseDouble( spotMapper.doubleToString( courseInfo.getRate() ) ) );
        courseDetailInfoDto.setMapStaticImageUrl( courseInfo.getMapStaticImageUrl() );
        courseDetailInfoDto.setDescription( courseInfo.getDescription() );
        courseDetailInfoDto.setSpots( courseSpotInfoListToCourseSpotInfoDtoList( courseInfo.getCourseSpotList() ) );
        courseDetailInfoDto.setCreatedDate( courseInfo.getCreatedDate() );
        courseDetailInfoDto.setLineStringJson( courseInfo.getLineStringJson() );

        return courseDetailInfoDto;
    }

    @Override
    public CourseDetailInfoDto.CourseSpotInfoDto toCourseSpotInfoDto(CourseInfo.CourseSpotInfo courseSpotInfo) {
        if ( courseSpotInfo == null ) {
            return null;
        }

        CourseDetailInfoDto.CourseSpotInfoDto courseSpotInfoDto = new CourseDetailInfoDto.CourseSpotInfoDto();

        courseSpotInfoDto.setId( courseSpotInfo.getId() );
        courseSpotInfoDto.setOrder( intToString( courseSpotInfo.getOrder() ) );
        courseSpotInfoDto.setPlaceName( courseSpotInfo.getPlaceName() );
        List<String> list = courseSpotInfo.getImageUrlList();
        if ( list != null ) {
            courseSpotInfoDto.setImageUrlList( new ArrayList<String>( list ) );
        }
        courseSpotInfoDto.setRate( Double.parseDouble( spotMapper.doubleToString( courseSpotInfo.getRate() ) ) );
        courseSpotInfoDto.setDescription( courseSpotInfo.getDescription() );
        courseSpotInfoDto.setCreateDate( spotMapper.localDateTimeToString( courseSpotInfo.getCreateDate() ) );
        courseSpotInfoDto.setPlaceAddress( courseSpotInfo.getPlaceAddress() );

        return courseSpotInfoDto;
    }

    @Override
    public MyCoursesResponse of(CourseInfo.MyCoursesResponse myCoursesResponse) {
        if ( myCoursesResponse == null ) {
            return null;
        }

        MyCoursesResponse.MyCoursesResponseBuilder myCoursesResponse1 = MyCoursesResponse.builder();

        myCoursesResponse1.content( courseListInfoListToCourseInfoList( myCoursesResponse.getContent() ) );
        myCoursesResponse1.totalPages( myCoursesResponse.getTotalPages() );

        return myCoursesResponse1.build();
    }

    @Override
    public MyCoursesResponse.CourseInfo of(CourseInfo.CourseListInfo courseListInfo) {
        if ( courseListInfo == null ) {
            return null;
        }

        MyCoursesResponse.CourseInfo.CourseInfoBuilder courseInfo = MyCoursesResponse.CourseInfo.builder();

        courseInfo.courseId( courseListInfo.getCourseId() );
        courseInfo.title( courseListInfo.getTitle() );
        courseInfo.rate( courseListInfo.getRate() );
        courseInfo.spotCount( courseListInfo.getSpotCount() );
        if ( courseListInfo.getCreatedDate() != null ) {
            courseInfo.createdDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( courseListInfo.getCreatedDate() ) );
        }
        courseInfo.mapStaticImageUrl( courseListInfo.getMapStaticImageUrl() );
        courseInfo.isPrivate( courseListInfo.getIsPrivate() );

        return courseInfo.build();
    }

    @Override
    public CourseSearchResponse toCourseSearchResponse(CourseInfo.Slice slice) {
        if ( slice == null ) {
            return null;
        }

        List<CourseDto> courses = null;
        boolean hasNext = false;

        courses = courseSearchInfoListToCourseDtoList( slice.getCourses() );
        hasNext = slice.isHasNext();

        CourseSearchResponse courseSearchResponse = new CourseSearchResponse( courses, hasNext );

        return courseSearchResponse;
    }

    @Override
    public CourseDto toCourseDto(CourseInfo.CourseSearchInfo courseSearchInfo) {
        if ( courseSearchInfo == null ) {
            return null;
        }

        LocalDateTime createDate = null;
        Long id = null;
        String title = null;
        String mapStaticImageUrl = null;
        String ownerProfileImageUrl = null;
        String ownerNickname = null;
        int spotCount = 0;
        double rate = 0.0d;
        boolean liked = false;

        createDate = courseSearchInfo.getCreateDate();
        id = courseSearchInfo.getId();
        title = courseSearchInfo.getTitle();
        mapStaticImageUrl = courseSearchInfo.getMapStaticImageUrl();
        ownerProfileImageUrl = courseSearchInfo.getOwnerProfileImageUrl();
        ownerNickname = courseSearchInfo.getOwnerNickname();
        spotCount = courseSearchInfo.getSpotCount();
        rate = courseSearchInfo.getRate();
        liked = courseSearchInfo.isLiked();

        CourseDto courseDto = new CourseDto( id, title, mapStaticImageUrl, ownerProfileImageUrl, ownerNickname, spotCount, rate, liked, createDate );

        return courseDto;
    }

    @Override
    public MySpotsDetailResponse toMySpotsDetailResponse(CourseInfo.MySpotsInfo infos) {
        if ( infos == null ) {
            return null;
        }

        MySpotsDetailResponse mySpotsDetailResponse = new MySpotsDetailResponse();

        mySpotsDetailResponse.setSpotDetails( toMySpotDetailDtoList( infos.getMySpotDetailDtoList() ) );

        return mySpotsDetailResponse;
    }

    @Override
    public List<MySpotsDetailResponse.SpotDetailDto> toMySpotDetailDtoList(List<CourseInfo.MySpotDetailDto> mySpotDetails) {
        if ( mySpotDetails == null ) {
            return null;
        }

        List<MySpotsDetailResponse.SpotDetailDto> list = new ArrayList<MySpotsDetailResponse.SpotDetailDto>( mySpotDetails.size() );
        for ( CourseInfo.MySpotDetailDto mySpotDetailDto : mySpotDetails ) {
            list.add( mySpotDetailDtoToSpotDetailDto( mySpotDetailDto ) );
        }

        return list;
    }

    protected List<CourseInfoDto> courseInPlaceInfoListToCourseInfoDtoList(List<CourseInfo.CourseInPlaceInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseInfoDto> list1 = new ArrayList<CourseInfoDto>( list.size() );
        for ( CourseInfo.CourseInPlaceInfo courseInPlaceInfo : list ) {
            list1.add( toDto( courseInPlaceInfo ) );
        }

        return list1;
    }

    protected List<SpotCommand.RegisterSpotRequest> spotRegisterRequestListToRegisterSpotRequestList(List<SpotRegisterRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<SpotCommand.RegisterSpotRequest> list1 = new ArrayList<SpotCommand.RegisterSpotRequest>( list.size() );
        for ( SpotRegisterRequest spotRegisterRequest : list ) {
            list1.add( spotMapper.toRegisterSpotRequest( spotRegisterRequest ) );
        }

        return list1;
    }

    protected List<SpotCommand.ModifySpotRequest> spotUpdateRequestListToModifySpotRequestList(List<SpotUpdateRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<SpotCommand.ModifySpotRequest> list1 = new ArrayList<SpotCommand.ModifySpotRequest>( list.size() );
        for ( SpotUpdateRequest spotUpdateRequest : list ) {
            list1.add( spotMapper.toModifySpotRequest( spotUpdateRequest ) );
        }

        return list1;
    }

    protected List<CourseDetailInfoDto.CourseSpotInfoDto> courseSpotInfoListToCourseSpotInfoDtoList(List<CourseInfo.CourseSpotInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseDetailInfoDto.CourseSpotInfoDto> list1 = new ArrayList<CourseDetailInfoDto.CourseSpotInfoDto>( list.size() );
        for ( CourseInfo.CourseSpotInfo courseSpotInfo : list ) {
            list1.add( toCourseSpotInfoDto( courseSpotInfo ) );
        }

        return list1;
    }

    protected List<MyCoursesResponse.CourseInfo> courseListInfoListToCourseInfoList(List<CourseInfo.CourseListInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<MyCoursesResponse.CourseInfo> list1 = new ArrayList<MyCoursesResponse.CourseInfo>( list.size() );
        for ( CourseInfo.CourseListInfo courseListInfo : list ) {
            list1.add( of( courseListInfo ) );
        }

        return list1;
    }

    protected List<CourseDto> courseSearchInfoListToCourseDtoList(List<CourseInfo.CourseSearchInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseDto> list1 = new ArrayList<CourseDto>( list.size() );
        for ( CourseInfo.CourseSearchInfo courseSearchInfo : list ) {
            list1.add( toCourseDto( courseSearchInfo ) );
        }

        return list1;
    }

    protected MySpotsDetailResponse.PointDto pointInfoToPointDto(CourseInfo.PointInfo pointInfo) {
        if ( pointInfo == null ) {
            return null;
        }

        MySpotsDetailResponse.PointDto.PointDtoBuilder pointDto = MySpotsDetailResponse.PointDto.builder();

        pointDto.x( pointInfo.getX() );
        pointDto.y( pointInfo.getY() );

        return pointDto.build();
    }

    protected MySpotsDetailResponse.SpotDetailDto mySpotDetailDtoToSpotDetailDto(CourseInfo.MySpotDetailDto mySpotDetailDto) {
        if ( mySpotDetailDto == null ) {
            return null;
        }

        MySpotsDetailResponse.SpotDetailDto.SpotDetailDtoBuilder spotDetailDto = MySpotsDetailResponse.SpotDetailDto.builder();

        spotDetailDto.spotId( mySpotDetailDto.getSpotId() );
        spotDetailDto.placeName( mySpotDetailDto.getPlaceName() );
        spotDetailDto.placeAddress( mySpotDetailDto.getPlaceAddress() );
        spotDetailDto.rate( mySpotDetailDto.getRate() );
        spotDetailDto.description( mySpotDetailDto.getDescription() );
        List<String> list = mySpotDetailDto.getImageUrls();
        if ( list != null ) {
            spotDetailDto.imageUrls( new ArrayList<String>( list ) );
        }
        spotDetailDto.createDate( mySpotDetailDto.getCreateDate() );
        spotDetailDto.point( pointInfoToPointDto( mySpotDetailDto.getPoint() ) );

        return spotDetailDto.build();
    }
}
