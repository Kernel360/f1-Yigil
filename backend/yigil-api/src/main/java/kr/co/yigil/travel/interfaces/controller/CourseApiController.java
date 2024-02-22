package kr.co.yigil.travel.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.CourseFacade;
import kr.co.yigil.travel.interfaces.dto.CourseDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.mapper.CourseMapper;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.CourseRegisterResponse;
import kr.co.yigil.travel.interfaces.dto.response.CourseUpdateResponse;
import kr.co.yigil.travel.interfaces.dto.response.CoursesInPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseApiController {

    private final CourseFacade courseFacade;
    private final CourseMapper courseMapper;

    @GetMapping("/place/{placeId}")
    public ResponseEntity<CoursesInPlaceResponse> getCoursesInPlace(
            @PathVariable("placeId") Long placeId,
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        var result = courseFacade.getCourseSliceInPlace(placeId, pageRequest);
        var response = courseMapper.courseSliceToCourseInPlaceResponse(result);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<CourseRegisterResponse> registerCourse(
            @ModelAttribute CourseRegisterRequest request,
            @Auth final Accessor accessor
    ) {
        Long memberId = accessor.getMemberId();
        var courseCommand = courseMapper.toRegisterCourseRequest(request);
        courseFacade.registerCourse(courseCommand, memberId);
        return ResponseEntity.ok().body(new CourseRegisterResponse("Course 생성 완료"));
    }

//    @PostMapping
//    @MemberOnly
//    public ResponseEntity<CourseRegisterResponse> registerCourseWithoutSeries(
//            @ModelAttribute CourseRegisterWithoutSeriesRequest request,
//            @Auth final Accessor accessor
//    ) {
//        Long memberId = accessor.getMemberId();
//        var courseCommand = courseRegisterm
//    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailInfoDto> retrieveCourse(@PathVariable("courseId") Long courseId) {
        var courseInfo = courseFacade.retrieveCourseInfo(courseId);
        var response = courseMapper.toCourseDetailInfoDto(courseInfo);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{courseId}")
    @MemberOnly
    public ResponseEntity<CourseUpdateResponse> updateCourse(
            @PathVariable("courseId") Long courseId,
            @ModelAttribute CourseUpdateRequest request,
            @Auth final Accessor accessor
    ) {
        Long memberId = accessor.getMemberId();
        var courseCommand = courseMapper.toModifyCourseRequest(request);
        courseFacade.modifyCourse(courseCommand, courseId, memberId);
        return ResponseEntity.ok().body(new CourseUpdateResponse("Course 수정 완료"));
    }
}
