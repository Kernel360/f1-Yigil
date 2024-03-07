package kr.co.yigil.travel.course.interfaces.controller;


import kr.co.yigil.global.SortBy;
import kr.co.yigil.global.SortOrder;
import kr.co.yigil.travel.course.application.CourseFacade;
import kr.co.yigil.travel.course.domain.CourseInfoDto;
import kr.co.yigil.travel.course.interfaces.dto.CourseDto;
import kr.co.yigil.travel.course.interfaces.dto.CourseDto.CourseDetailResponse;
import kr.co.yigil.travel.course.interfaces.dto.CourseDto.CoursesResponse;
import kr.co.yigil.travel.course.interfaces.dto.mapper.CourseDtoMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseApiController {

    private final CourseFacade courseFacade;
    private final CourseDtoMapper courseDtoMapper;

    @GetMapping
    public ResponseEntity<CoursesResponse> getCourses(
        @PageableDefault(size = 5, page = 1) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(
            pageable.getPageNumber() - 1,
            pageable.getPageSize(),
            Sort.by(direction, sortBy.getValue())
        );
        CourseInfoDto.CoursesPageInfo courses = courseFacade.getCourses(pageRequest);
        CoursesResponse response = courseDtoMapper.toPageDtp(courses);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailResponse> getCourse(
        @PathVariable Long courseId) {
        CourseInfoDto.CourseDetailInfo course = courseFacade.getCourse(courseId);
        CourseDetailResponse response = courseDtoMapper.toDetailDto(course);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<CourseDto.AdminCourseDeleteResponse> deleteCourse(
        @PathVariable Long courseId) {
        courseFacade.deleteCourse(courseId);
        return ResponseEntity.ok().body(new CourseDto.AdminCourseDeleteResponse("삭제 성공"));
    }


}
