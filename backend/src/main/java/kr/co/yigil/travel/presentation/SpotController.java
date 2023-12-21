package kr.co.yigil.travel.presentation;


import java.net.URI;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.post.dto.request.SpotRequest;
import kr.co.yigil.post.dto.response.SpotResponse;
import kr.co.yigil.travel.application.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SpotController {

    private final SpotService spotService;

    @PostMapping("/spot")
    @MemberOnly
    public ResponseEntity<Long> createSpotPost(
            @RequestBody SpotRequest spotRequest,
            @Auth final Accessor accessor

    ){
        Long spotId = spotService.createSpotPost(accessor.getMemberId(), spotRequest);
        URI uri = URI.create("api/v1/spot/" + spotId);
        return ResponseEntity.created(uri).body(spotId);
    }

    @PostMapping("/course/{courseId}/spot")
    @MemberOnly
    public ResponseEntity<SpotResponse> createSpot(
            @PathVariable Long courseId,
            @RequestBody SpotRequest spotRequest,
            @Auth final Accessor accessor
    ){
        SpotResponse spotResponse = spotService.createSpot(accessor.getMemberId(), courseId, spotRequest);
        URI uri = URI.create("api/v1/post/spot/" + spot.getId());
        return ResponseEntity.created(uri).body(spotResponse);
    }
    )


    @GetMapping("/spot/{postId}")
    @MemberOnly
    public ResponseEntity<SpotResponse> findSpot(
            @PathVariable Long postId
    ) {
        SpotResponse post = spotService.findSpot(postId);
        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/spot/{id}")
    public ResponseEntity<Long> updateSpot(
            @PathVariable Long id,
            @Auth final Accessor accessor,
            @RequestBody SpotRequest spotRequest
    ){
        Long updatedPost = spotService.updateSpot(accessor.getMemberId(), id, spotRequest);
        return ResponseEntity.ok().body(updatedPost);
    }

    @DeleteMapping("/spot/{postId}")
    @MemberOnly
    public ResponseEntity<String> deletePost(
            @PathVariable Long postId
    ){
        spotService.deleteSpot(postId);
        String message = "해당 게시글은 삭제되었습니다";
        return ResponseEntity.ok().body(message);
    }

}
