package kr.co.yigil.travel.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.CourseService;
import kr.co.yigil.travel.dto.request.CourseCreateRequest;
import kr.co.yigil.travel.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.dto.response.CourseCreateResponse;
import kr.co.yigil.travel.dto.response.CourseFindResponse;
import kr.co.yigil.travel.dto.response.CourseUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    @MemberOnly
    public ResponseEntity<CourseCreateResponse> createCourse(
        @RequestBody CourseCreateRequest courseCreateRequest,
        @Auth final Accessor accessor
    ){
        CourseCreateResponse courseCreateResponse =  courseService.createCourse(accessor.getMemberId(), courseCreateRequest);
        return ResponseEntity.ok(courseCreateResponse);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<CourseFindResponse> findCourse(
        @PathVariable("post_id") Long postId
    ){
        CourseFindResponse courseFindResponse = courseService.findCourse(postId);
        return ResponseEntity.ok().body(courseFindResponse);
    }

    @PutMapping("/{post_id}")
    @MemberOnly
    public ResponseEntity<CourseUpdateResponse> updateCourse(
        @PathVariable("post_id") Long postId,
        @RequestBody CourseUpdateRequest courseUpdateRequest,
        @Auth final Accessor accessor
    ){
        CourseUpdateResponse courseUpdateResponse = courseService.updateCourse( postId,accessor.getMemberId(), courseUpdateRequest);
        return ResponseEntity.ok().body(courseUpdateResponse);
    }

}
