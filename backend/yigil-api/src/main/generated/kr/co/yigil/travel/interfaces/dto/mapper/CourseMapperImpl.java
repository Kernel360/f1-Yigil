package kr.co.yigil.travel.interfaces.dto.mapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseCommand;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.domain.spot.SpotCommand;
import kr.co.yigil.travel.interfaces.dto.CourseDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.CourseInfoDto;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterWithoutSeriesRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.MyCoursesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-01T12:49:05+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Autowired
    private SpotMapper spotMapper;

    @Override
    public CourseInfoDto courseToCourseInfoDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseInfoDto courseInfoDto = new CourseInfoDto();

        courseInfoDto.setMapStaticImageFileUrl( course.getMapStaticImageFile().getFileUrl() );
        courseInfoDto.setTitle( course.getTitle() );
        courseInfoDto.setRate( String.valueOf(course.getRate()) );
        courseInfoDto.setSpotCount( String.valueOf(course.getSpots().size()) );
        courseInfoDto.setCreateDate( course.getCreatedAt().toString() );
        courseInfoDto.setOwnerProfileImageUrl( course.getMember().getProfileImageUrl() );
        courseInfoDto.setOwnerNickname( course.getMember().getNickname() );

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

        modifyCourseRequest.description( courseUpdateRequest.getDescription() );
        modifyCourseRequest.rate( courseUpdateRequest.getRate() );
        List<Long> list = courseUpdateRequest.getSpotIdOrder();
        if ( list != null ) {
            modifyCourseRequest.spotIdOrder( new ArrayList<Long>( list ) );
        }
        modifyCourseRequest.modifySpotRequests( spotUpdateRequestListToModifySpotRequestList( courseUpdateRequest.getCourseSpotUpdateRequests() ) );

        return modifyCourseRequest.build();
    }

    @Override
    public CourseDetailInfoDto toCourseDetailInfoDto(CourseInfo.Main courseInfo) {
        if ( courseInfo == null ) {
            return null;
        }

        CourseDetailInfoDto courseDetailInfoDto = new CourseDetailInfoDto();

        courseDetailInfoDto.setTitle( courseInfo.getTitle() );
        courseDetailInfoDto.setRate( spotMapper.doubleToString( courseInfo.getRate() ) );
        courseDetailInfoDto.setMapStaticImageUrl( courseInfo.getMapStaticImageUrl() );
        courseDetailInfoDto.setDescription( courseInfo.getDescription() );
        courseDetailInfoDto.setSpots( courseSpotInfoListToCourseSpotInfoDtoList( courseInfo.getCourseSpotList() ) );

        return courseDetailInfoDto;
    }

    @Override
    public CourseDetailInfoDto.CourseSpotInfoDto toCourseSpotInfoDto(CourseInfo.CourseSpotInfo courseSpotInfo) {
        if ( courseSpotInfo == null ) {
            return null;
        }

        CourseDetailInfoDto.CourseSpotInfoDto courseSpotInfoDto = new CourseDetailInfoDto.CourseSpotInfoDto();

        courseSpotInfoDto.setOrder( intToString( courseSpotInfo.getOrder() ) );
        courseSpotInfoDto.setPlaceName( courseSpotInfo.getPlaceName() );
        List<String> list = courseSpotInfo.getImageUrlList();
        if ( list != null ) {
            courseSpotInfoDto.setImageUrlList( new ArrayList<String>( list ) );
        }
        courseSpotInfoDto.setRate( spotMapper.doubleToString( courseSpotInfo.getRate() ) );
        courseSpotInfoDto.setDescription( courseSpotInfo.getDescription() );
        courseSpotInfoDto.setCreateDate( spotMapper.localDateTimeToString( courseSpotInfo.getCreateDate() ) );

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
}
