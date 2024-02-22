package kr.co.yigil.travel.interfaces.dto.mapper;

import kr.co.yigil.travel.domain.course.CourseCommand;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = SpotRegisterMapper.class)
public interface CourseRegisterMapper {

    CourseRegisterMapper INSTANCE = Mappers.getMapper(CourseRegisterMapper.class);

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
}
