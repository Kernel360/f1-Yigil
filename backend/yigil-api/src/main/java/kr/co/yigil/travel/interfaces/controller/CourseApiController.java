package kr.co.yigil.travel.interfaces.controller;


import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.Selected;
import kr.co.yigil.global.SortBy;
import kr.co.yigil.global.SortOrder;
import kr.co.yigil.travel.application.CourseFacade;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.interfaces.dto.CourseDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.mapper.CourseMapper;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseRegisterWithoutSeriesRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.request.MySpotsDetailRequest;
import kr.co.yigil.travel.interfaces.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseApiController {

    private final CourseFacade courseFacade;
    private final CourseMapper courseMapper;

    @GetMapping("/place/{placeId}")
    public ResponseEntity<CoursesInPlaceResponse> getCoursesInPlace(
            @PathVariable("placeId") Long placeId,
            @PageableDefault(size = 5, page = 1) Pageable pageable,
            @Auth Accessor accessor,
            @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by(direction, sortBy.getValue()));
        var result = courseFacade.getCourseSliceInPlace(placeId, accessor.getMemberId(), pageRequest);
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

    @PostMapping("/only")
    @MemberOnly
    public ResponseEntity<CourseRegisterResponse> registerCourseWithoutSeries(
            @ModelAttribute CourseRegisterWithoutSeriesRequest request,
            @Auth final Accessor accessor
    ) {
        Long memberId = accessor.getMemberId();
        var courseCommand = courseMapper.toRegisterCourseRequest(request);
        courseFacade.registerCourseWithoutSeries(courseCommand, memberId);
        return ResponseEntity.ok().body(new CourseRegisterResponse("Course 생성 완료"));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailInfoDto> retrieveCourse(
            @PathVariable("courseId") Long courseId) {
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

    @DeleteMapping("/{courseId}")
    @MemberOnly
    public ResponseEntity<CourseDeleteResponse> deleteSpot(
            @PathVariable("courseId") Long courseId,
            @Auth final Accessor accessor
    ) {
        Long memberId = accessor.getMemberId();
        courseFacade.deleteCourse(courseId, memberId);
        return ResponseEntity.ok().body(new CourseDeleteResponse("Course 삭제 완료"));
    }

    @GetMapping("/my")
    @MemberOnly
    public ResponseEntity<MyCoursesResponse> getMyCourseList(
            @Auth final Accessor accessor,
            @PageableDefault(size = 5, page = 1) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder,
            @RequestParam(name = "selected", defaultValue = "all", required = false) Selected visibility
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by(direction, sortBy.getValue()));

        final var memberCoursesInfo = courseFacade.getMemberCoursesInfo(
                accessor.getMemberId(), pageRequest, visibility);
        var myCoursesResponse = courseMapper.of(memberCoursesInfo);
        return ResponseEntity.ok().body(myCoursesResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<CourseSearchResponse> searchCourseByPlaceName(
            @Auth Accessor accessor,
            @PageableDefault(size = 5, page = 1) Pageable pageable,
            @RequestParam String keyword,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder,
            @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by(direction, sortBy.getValue()));

        var result = courseFacade.searchCourseByPlaceName(keyword, accessor, pageRequest);
        var response = courseMapper.toCourseSearchResponse(result);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/spots")
    public ResponseEntity<MySpotsDetailResponse> getMySpotsDetailInfo(
            @RequestBody MySpotsDetailRequest request,
            @Auth final Accessor accessor
    ) {
        Long memberId = accessor.getMemberId();
        var infos = courseFacade.getMySpotsDetailInfo(request.getSpotIds(), memberId);
        MySpotsDetailResponse response = courseMapper.toMySpotsDetailResponse(infos);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/my/favorite")
    @MemberOnly
    public ResponseEntity<MyFavoriteCoursesResponse> getMyFavoriteCourses(
            @Auth final Accessor accessor,
            @PageableDefault(size = 5, page = 1) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "title", required = false) SortBy sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) SortOrder sortOrder
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by(direction, sortBy.getValue()));
        CourseInfo.MyFavoriteCoursesInfo result = courseFacade.getFavoriteCoursesInfo(accessor.getMemberId(), pageRequest);
        MyFavoriteCoursesResponse response = courseMapper.toMyFavoriteCoursesResponse(result);
        return ResponseEntity.ok().body(response);
    }

}
