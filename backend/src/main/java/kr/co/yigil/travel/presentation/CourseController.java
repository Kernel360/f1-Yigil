package kr.co.yigil.travel.presentation;

import java.net.URI;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.travel.dto.request.CourseCreateRequest;
import kr.co.yigil.travel.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.dto.response.CourseResponse;
import kr.co.yigil.travel.application.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final PostService postService;

    @PostMapping("/course")
    @MemberOnly
    public ResponseEntity<Long> createCourse(
            @RequestBody CourseCreateRequest courseCreateRequest,
            @Auth final Accessor accessor
    ){
        Long courseId =  courseService.createCourse(accessor.getMemberId(), courseCreateRequest);
        URI uri = URI.create("api/v1/post/course" + courseId);
        return ResponseEntity.created(uri).body(courseId);
    }

    @GetMapping("/course/{postId}")
    public ResponseEntity<CourseResponse> findCourse(
            @PathVariable Long postId
    ){
        CourseResponse post = courseService.findCourse(postId);
        return ResponseEntity.ok().body(post);
    }

    @PutMapping("/course/{postId}")
    @MemberOnly
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long postId,
            @RequestBody CourseUpdateRequest courseUpdateRequest,
            @Auth final Accessor accessor
    ){
        return ResponseEntity.ok().body(courseService.updateCourse( postId,accessor.getMemberId(), courseUpdateRequest));
    }

    @DeleteMapping("/course/{postId}")
    @MemberOnly
    public ResponseEntity<String> deleteCoursePost(
            @PathVariable Long postId,
            @Auth final Accessor accessor
    ){
        postService.deletePost(accessor.getMemberId(), postId);
        String message = "해당 코스 삭제되었습니다";
        return ResponseEntity.ok().body(message);
    }


}
