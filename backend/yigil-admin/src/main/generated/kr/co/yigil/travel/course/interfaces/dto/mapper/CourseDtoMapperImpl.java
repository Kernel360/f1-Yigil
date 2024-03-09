package kr.co.yigil.travel.course.interfaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.travel.course.domain.CourseInfoDto;
import kr.co.yigil.travel.course.interfaces.dto.CourseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-08T10:55:32+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CourseDtoMapperImpl implements CourseDtoMapper {

    @Override
    public CourseDto.CourseListUnit of(CourseInfoDto.CourseListUnit course) {
        if ( course == null ) {
            return null;
        }

        CourseDto.CourseListUnit courseListUnit = new CourseDto.CourseListUnit();

        courseListUnit.setCourseId( course.getCourseId() );
        courseListUnit.setTitle( course.getTitle() );
        courseListUnit.setCreatedAt( course.getCreatedAt() );
        courseListUnit.setFavorCount( course.getFavorCount() );
        courseListUnit.setCommentCount( course.getCommentCount() );

        return courseListUnit;
    }

    @Override
    public CourseDto.CourseDetailResponse toDetailDto(CourseInfoDto.CourseDetailInfo course) {
        if ( course == null ) {
            return null;
        }

        CourseDto.CourseDetailResponse courseDetailResponse = new CourseDto.CourseDetailResponse();

        courseDetailResponse.setCourseId( course.getCourseId() );
        courseDetailResponse.setTitle( course.getTitle() );
        courseDetailResponse.setContent( course.getContent() );
        courseDetailResponse.setMapStaticImageUrl( course.getMapStaticImageUrl() );
        courseDetailResponse.setCreatedAt( course.getCreatedAt() );
        courseDetailResponse.setRate( course.getRate() );
        courseDetailResponse.setFavorCount( course.getFavorCount() );
        courseDetailResponse.setCommentCount( course.getCommentCount() );
        courseDetailResponse.setWriterId( course.getWriterId() );
        courseDetailResponse.setWriterName( course.getWriterName() );

        return courseDetailResponse;
    }
}
