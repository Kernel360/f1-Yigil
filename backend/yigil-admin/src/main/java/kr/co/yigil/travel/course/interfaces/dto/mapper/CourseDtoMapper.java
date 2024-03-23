package kr.co.yigil.travel.course.interfaces.dto.mapper;

import kr.co.yigil.travel.course.domain.CourseInfoDto;
import kr.co.yigil.travel.course.domain.CourseInfoDto.CourseDetailInfo;
import kr.co.yigil.travel.course.domain.CourseInfoDto.CourseListUnit;
import kr.co.yigil.travel.course.domain.CourseInfoDto.CoursesPageInfo;
import kr.co.yigil.travel.course.interfaces.dto.CourseDto;
import kr.co.yigil.travel.course.interfaces.dto.CourseDto.CourseDetailResponse;
import kr.co.yigil.travel.course.interfaces.dto.CourseDto.CoursesResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CourseDtoMapper {

    default CoursesResponse toPageDtp(CoursesPageInfo courses){
        return new CoursesResponse(mapToPage(courses.getCourses()));
    }

    CourseDto.CourseListUnit of(CourseInfoDto.CourseListUnit course);

    CourseDetailResponse toDetailDto(CourseDetailInfo course);

    List<CourseDto.SpotDetailDto> toSpotDetailDto(List<CourseInfoDto.SpotDetailInfo> spotDetailInfos);


    default Page<CourseDto.CourseListUnit> mapToPage(Page<CourseListUnit> page) {
        return new PageImpl<>(
            page.getContent().stream().map(this::of)
            .toList(),
            page.getPageable(),
            page.getTotalElements()
        );
    }
}
