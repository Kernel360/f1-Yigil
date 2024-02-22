package kr.co.yigil.follow.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.follow.application.FollowFacade;
import kr.co.yigil.follow.dto.response.FollowResponse;
import kr.co.yigil.follow.dto.response.UnfollowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowFacade followFacade;
    @PostMapping("/api/v1/follow/{memberId}")
    @MemberOnly
    public ResponseEntity<FollowResponse> follow(@Auth final Accessor accessor,
            @PathVariable("memberId") final Long memberId) {
        followFacade.follow(accessor.getMemberId(), memberId);
        return ResponseEntity.ok().body(new FollowResponse("팔로우 성공"));
    }

    @PostMapping("/api/v1/unfollow/{memberId}")
    @MemberOnly
    public ResponseEntity<UnfollowResponse> unfollow(@Auth final Accessor accessor,
            @PathVariable("memberId") final Long memberId) {

        followFacade.unfollow(accessor.getMemberId(), memberId);
        return ResponseEntity.ok().body(new UnfollowResponse("언팔로우 성공"));
    }
}
