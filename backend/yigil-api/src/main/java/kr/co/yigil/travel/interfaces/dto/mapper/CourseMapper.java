package kr.co.yigil.travel.interfaces.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.interfaces.dto.CourseInfoDto;
import kr.co.yigil.travel.interfaces.dto.response.CoursesInPlaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "mapStaticImageFileUrl", expression = "java(course.getMapStaticImageFile().getFileUrl())")
    @Mapping(target = "title", expression = "java(course.getTitle())")
    @Mapping(target = "rate", expression = "java(String.valueOf(course.getRate()))")
    @Mapping(target = "courseCount", expression = "java(String.valueOf(course.getSpots().size()))")
    @Mapping(target = "createDate", expression = "java(course.getCreatedAt().toString())")
    @Mapping(target = "ownerProfileImageUrl", expression = "java(course.getMember().getProfileImageUrl())")
    @Mapping(target = "ownerNickname", expression = "java(course.getMember().getNickname())")
    CourseInfoDto courseToCourseInfoDto(Course course);

    default List<CourseInfoDto> coursesToCourseInfoDtoList(List<Course> courses) {
        return courses.stream()
                .map(this::courseToCourseInfoDto)
                .collect(Collectors.toList());
    }

    default CoursesInPlaceResponse courseSliceToCourseInPlaceResponse(Slice<Course> courseSlice) {
        List<CourseInfoDto> courseInfoDtoList = coursesToCourseInfoDtoList(courseSlice.getContent());
        boolean hasNext = courseSlice.hasNext();
        return new CoursesInPlaceResponse(courseInfoDtoList, hasNext);
    }

}
