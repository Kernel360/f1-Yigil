package kr.co.yigil.travel.presentation;

import java.net.URI;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.Course2Service;
import kr.co.yigil.travel.interfaces.dto.request.CourseCreateRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.CourseCreateResponse;
import kr.co.yigil.travel.interfaces.dto.response.CourseDeleteResponse;
import kr.co.yigil.travel.interfaces.dto.response.CourseFindDto;
import kr.co.yigil.travel.interfaces.dto.response.CourseInfoResponse;
import kr.co.yigil.travel.interfaces.dto.response.CourseUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final Course2Service courseService;

    @GetMapping("/places/{place_id}")
    public ResponseEntity<Slice<CourseFindDto>> getCourseList(
            @PathVariable("place_id") Long placeId,
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Slice<CourseFindDto> courseListResponse = courseService.getCourseList(placeId, pageRequest);
        return ResponseEntity.ok().body(courseListResponse);
    }

//    @PostMapping
//    @MemberOnly
//    public ResponseEntity<CourseCreateResponse> createCourse(
//        @ModelAttribute CourseCreateRequest courseCreateRequest,
//        @Auth final Accessor accessor
//    ) {
//        CourseCreateResponse courseCreateResponse = courseService.createCourse(
//            accessor.getMemberId(), courseCreateRequest);
//        URI uri = URI.create("/api/v1/courses/" + courseCreateResponse.getCourseId());
//        return ResponseEntity.created(uri).body(courseCreateResponse);
//    }

    @GetMapping("/{course_id}")
    public ResponseEntity<CourseInfoResponse> getCourseInfo(
        @PathVariable("course_id") Long courseId
    ) {
        CourseInfoResponse courseInfoResponse = courseService.getCourseInfo(courseId);
        return ResponseEntity.ok().body(courseInfoResponse);
    }

    @PostMapping("/{course_id}")
    @MemberOnly
    public ResponseEntity<CourseUpdateResponse> updateCourse(
        @PathVariable("course_id") Long courseId,
        @ModelAttribute CourseUpdateRequest courseUpdateRequest,
        @Auth final Accessor accessor
    ) {
        CourseUpdateResponse courseUpdateResponse = courseService.updateCourse(courseId,
            accessor.getMemberId(), courseUpdateRequest);
        URI uri = URI.create("/api/v1/courses/" + courseId);
        return ResponseEntity.ok()
            .location(uri)
            .body(courseUpdateResponse);
    }

    @DeleteMapping("/{course_id}")
    @MemberOnly
    public ResponseEntity<CourseDeleteResponse> deleteCourse(
        @PathVariable("course_id") Long courseId,
        @Auth final Accessor accessor
    ) {
        CourseDeleteResponse courseDeleteResponse = courseService.deleteCourse(courseId,
            accessor.getMemberId());
        return ResponseEntity.ok().body(courseDeleteResponse);
    }
}
