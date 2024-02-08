package kr.co.yigil.follow.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.follow.application.FollowService;
import kr.co.yigil.follow.dto.response.FollowResponse;
import kr.co.yigil.follow.dto.response.UnfollowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * /api/v1 공통 분리 고려
 */
@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/api/v1/follow/{memberId}")
    @MemberOnly
    public ResponseEntity<FollowResponse> follow(@Auth final Accessor accessor,
            @PathVariable("memberId") final Long memberId) {
        FollowResponse response = followService.follow(accessor.getMemberId(), memberId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/v1/unfollow/{memberId}")
    @MemberOnly
    public ResponseEntity<UnfollowResponse> unfollow(@Auth final Accessor accessor,
            @PathVariable("memberId") final Long memberId) {
        UnfollowResponse response = followService.unfollow(accessor.getMemberId(), memberId);
        return ResponseEntity.ok().body(response);
    }
}
