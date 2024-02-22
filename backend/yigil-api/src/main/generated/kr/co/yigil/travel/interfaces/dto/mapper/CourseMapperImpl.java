package kr.co.yigil.travel.interfaces.dto.mapper;

import javax.annotation.processing.Generated;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.interfaces.dto.CourseInfoDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-21T18:09:54+0900",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseInfoDto courseToCourseInfoDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseInfoDto courseInfoDto = new CourseInfoDto();

        courseInfoDto.setMapStaticImageFileUrl( course.getMapStaticImageFile().getFileUrl() );
        courseInfoDto.setTitle( course.getTitle() );
        courseInfoDto.setRate( String.valueOf(course.getRate()) );
        courseInfoDto.setCourseCount( String.valueOf(course.getSpots().size()) );
        courseInfoDto.setCreateDate( course.getCreatedAt().toString() );
        courseInfoDto.setOwnerProfileImageUrl( course.getMember().getProfileImageUrl() );
        courseInfoDto.setOwnerNickname( course.getMember().getNickname() );

        return courseInfoDto;
    }
}
