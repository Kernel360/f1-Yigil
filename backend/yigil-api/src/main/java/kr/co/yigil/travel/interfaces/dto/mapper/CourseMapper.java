package kr.co.yigil.travel.interfaces.dto.mapper;

import kr.co.yigil.travel.domain.course.CourseCommand;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.interfaces.dto.CourseDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.CourseDto;
import kr.co.yigil.travel.interfaces.dto.CourseInfoDto;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterWithoutSeriesRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.*;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SpotMapper.class}
)
public interface CourseMapper {

    CoursesInPlaceResponse courseSliceToCourseInPlaceResponse(CourseInfo.CoursesInPlaceResponseInfo courseSlice) ;
    CourseInfoDto toDto(CourseInfo.CourseInPlaceInfo courseInPlaceInfo);

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
            @Mapping(source = "spotIdOrder", target = "spotIdOrder"),
            @Mapping(source = "courseSpotUpdateRequests", target = "modifySpotRequests")
    })
    CourseCommand.ModifyCourseRequest toModifyCourseRequest(CourseUpdateRequest courseUpdateRequest);

    default LineString map(String lineStringJson) throws ParseException {
        return (LineString) new GeoJsonReader().read(lineStringJson);
    }



    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "rate", target = "rate", qualifiedByName = "doubleToString"),
            @Mapping(source = "mapStaticImageUrl", target = "mapStaticImageUrl"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "courseSpotList", target = "spots")
    })
    CourseDetailInfoDto toCourseDetailInfoDto(CourseInfo.Main courseInfo);

    @Mappings({
            @Mapping(source = "id", target = "id"),
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

    MyFavoriteCoursesResponse toMyFavoriteCoursesResponse(CourseInfo.MyFavoriteCoursesInfo result);
}
