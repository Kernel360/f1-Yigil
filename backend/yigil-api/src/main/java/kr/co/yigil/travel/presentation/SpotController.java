package kr.co.yigil.travel.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotFindResponse;
import kr.co.yigil.travel.application.SpotService;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spots")
public class SpotController {
    private final SpotService spotService;

    @PostMapping
//    @MemberOnly
    public ResponseEntity<SpotCreateResponse> createSpot(
        @ModelAttribute SpotCreateRequest spotCreateRequest,
        @Auth final Accessor accessor
    ){
        SpotCreateResponse spotCreateResponse = spotService.createSpot(accessor.getMemberId(), spotCreateRequest);
        return ResponseEntity.ok().body(spotCreateResponse);
    }

//    @PostMapping("/course/{courseId}/spot")
//    @MemberOnly
//    public ResponseEntity<SpotFindResponse> createSpotInCourse(
//            @PathVariable Long courseId,
//            @RequestBody SpotCreateRequest spotRequest,
//            @Auth final Accessor accessor
//    ){
//        SpotFindResponse spotResponse = spotService.createSpot(accessor.getMemberId(), courseId, spotRequest);
//        URI uri = URI.create("api/v1/post/spot/" + spot.getId());
//        return ResponseEntity.created(uri).body(spotResponse);
//    }

    @GetMapping("/{post_id}")
    public ResponseEntity<SpotFindResponse> findSpot(
        @PathVariable("post_id") Long postId
    ) {
        SpotFindResponse post = spotService.findSpotByPostId(postId);
        return ResponseEntity.ok().body(post);
    }

    // public findAllSpotPost() - 코스에 넣을 spot list
    @PostMapping("/{post_id}")
//    @MemberOnly
    public ResponseEntity<SpotUpdateResponse> updateSpot(
        @PathVariable("post_id") Long postId,
        @Auth final Accessor accessor,
        @ModelAttribute SpotUpdateRequest spotUpdateRequest
    ){
//        SpotUpdateResponse spotUpdateResponse = spotService.updateSpot(1L, postId, spotUpdateRequest);
        SpotUpdateResponse spotUpdateResponse = spotService.updateSpot(accessor.getMemberId(), postId, spotUpdateRequest);
        return ResponseEntity.ok().body(spotUpdateResponse);
    }


}
