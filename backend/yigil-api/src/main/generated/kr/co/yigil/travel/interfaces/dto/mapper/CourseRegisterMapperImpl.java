package kr.co.yigil.travel.interfaces.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.travel.domain.course.CourseCommand;
import kr.co.yigil.travel.domain.spot.SpotCommand;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-22T12:09:38+0900",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CourseRegisterMapperImpl implements CourseRegisterMapper {

    @Autowired
    private SpotRegisterMapper spotRegisterMapper;

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

    protected List<SpotCommand.RegisterSpotRequest> spotRegisterRequestListToRegisterSpotRequestList(List<SpotRegisterRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<SpotCommand.RegisterSpotRequest> list1 = new ArrayList<SpotCommand.RegisterSpotRequest>( list.size() );
        for ( SpotRegisterRequest spotRegisterRequest : list ) {
            list1.add( spotRegisterMapper.toRegisterSpotRequest( spotRegisterRequest ) );
        }

        return list1;
    }
}
