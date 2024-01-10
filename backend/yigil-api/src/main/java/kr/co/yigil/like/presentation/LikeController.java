package kr.co.yigil.like.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.like.application.LikeService;
import kr.co.yigil.like.dto.response.LikeResponse;
import kr.co.yigil.like.dto.response.UnlikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/v1/like/{postId}")
    @MemberOnly
    public ResponseEntity<LikeResponse> like(@Auth final Accessor accessor,
            @PathVariable("postId") final Long postId) {
        LikeResponse response = likeService.like(accessor.getMemberId(), postId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/v1/unlike/{postId}")
    @MemberOnly
    public ResponseEntity<UnlikeResponse> unlike(@Auth final Accessor accessor,
            @PathVariable("postId") final Long postId) {
        UnlikeResponse response = likeService.unlike(accessor.getMemberId(), postId);
        return ResponseEntity.ok().body(response);
    }
}
