package kr.co.yigil.travel.interfaces.dto.mapper;

import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseCommand;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.interfaces.dto.CourseDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.CourseDto;
import kr.co.yigil.travel.interfaces.dto.CourseInfoDto;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterWithoutSeriesRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.CourseSearchResponse;
import kr.co.yigil.travel.interfaces.dto.response.CoursesInPlaceResponse;
import kr.co.yigil.travel.interfaces.dto.response.MyCoursesResponse;
import kr.co.yigil.travel.interfaces.dto.response.MySpotsDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {SpotMapper.class})
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "mapStaticImageFileUrl", expression = "java(course.getMapStaticImageFile().getFileUrl())")
    @Mapping(target = "title", expression = "java(course.getTitle())")
    @Mapping(target = "rate", expression = "java(String.valueOf(course.getRate()))")
    @Mapping(target = "spotCount", expression = "java(String.valueOf(course.getSpots().size()))")
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

    @Mappings({
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "rate", source = "rate"),
            @Mapping(target = "isPrivate", source = "private"),
            @Mapping(target = "representativeSpotOrder", source = "representativeSpotOrder"),
            @Mapping(target = "lineStringJson", source = "lineStringJson"),
            @Mapping(target = "mapStaticImageFile", source = "mapStaticImageFile"),
            @Mapping(target = "registerSpotRequests", source = "spotRegisterRequests")
    })
    CourseCommand.RegisterCourseRequest toRegisterCourseRequest(CourseRegisterRequest request);

    @Mappings({
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "rate", source = "rate"),
            @Mapping(target = "isPrivate", source = "private"),
            @Mapping(target = "representativeSpotOrder", source = "representativeSpotOrder"),
            @Mapping(target = "lineStringJson", source = "lineStringJson"),
            @Mapping(target = "mapStaticImageFile", source = "mapStaticImageFile"),
            @Mapping(target = "spotIds", source = "spotIds")
    })
    CourseCommand.RegisterCourseRequestWithSpotInfo toRegisterCourseRequest(
            CourseRegisterWithoutSeriesRequest request);

    @Mappings({
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "rate", target = "rate"),
            @Mapping(source = "spotIdOrder", target = "spotIdOrder"),
            @Mapping(source = "courseSpotUpdateRequests", target = "modifySpotRequests")
    })
    CourseCommand.ModifyCourseRequest toModifyCourseRequest(CourseUpdateRequest courseUpdateRequest);

    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "rate", target = "rate", qualifiedByName = "doubleToString"),
            @Mapping(source = "mapStaticImageUrl", target = "mapStaticImageUrl"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "courseSpotList", target = "spots")
    })
    CourseDetailInfoDto toCourseDetailInfoDto(CourseInfo.Main courseInfo);

    @Mappings({
            @Mapping(source = "order", target = "order", qualifiedByName = "intToString"),
            @Mapping(source = "placeName", target = "placeName"),
            @Mapping(source = "imageUrlList", target = "imageUrlList"),
            @Mapping(source = "rate", target = "rate", qualifiedByName = "doubleToString"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "createDate", target = "createDate", qualifiedByName = "localDateTimeToString")
    })
    CourseDetailInfoDto.CourseSpotInfoDto toCourseSpotInfoDto(CourseInfo.CourseSpotInfo courseSpotInfo);

    @Named("intToString")
    default String intToString(int value) {
        return Integer.toString(value);
    }

    MyCoursesResponse of (CourseInfo.MyCoursesResponse myCoursesResponse);
    MyCoursesResponse.CourseInfo of (CourseInfo.CourseListInfo courseListInfo);

    @Mapping(source = "courses", target = "courses")
    CourseSearchResponse toCourseSearchResponse(CourseInfo.Slice slice);

    @Mapping(target = "createDate", source = "createDate")
    CourseDto toCourseDto(CourseInfo.CourseSearchInfo courseSearchInfo);

    @Mapping(target = "spotDetails", source = "mySpotDetailDtoList")
    MySpotsDetailResponse toMySpotsDetailResponse(CourseInfo.MySpotsInfo infos);

    @Mapping(target = "imageUrls", source = "imageUrls")
    List<MySpotsDetailResponse.SpotDetailDto> toMySpotDetailDtoList(List<CourseInfo.MySpotDetailDto> mySpotDetails);
}
