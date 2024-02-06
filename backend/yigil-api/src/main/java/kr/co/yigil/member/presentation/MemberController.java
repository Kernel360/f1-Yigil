package kr.co.yigil.member.presentation;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.dto.response.MemberCourseResponse;
import kr.co.yigil.member.dto.response.MemberDeleteResponse;
import kr.co.yigil.member.dto.response.MemberFollowerListResponse;
import kr.co.yigil.member.dto.response.MemberFollowingListResponse;
import kr.co.yigil.member.dto.response.MemberInfoResponse;
import kr.co.yigil.member.dto.response.MemberSpotResponse;
import kr.co.yigil.member.dto.response.MemberUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    @MemberOnly
    public ResponseEntity<MemberInfoResponse> getMyInfo(@Auth final Accessor accessor) {
        final MemberInfoResponse response = memberService.getMemberInfo(accessor.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/api/v1/members/courses")
    @MemberOnly
    public ResponseEntity<MemberCourseResponse> getMyCourseInfo(@Auth final Accessor accessor) {
        final MemberCourseResponse response = memberService.getMemberCourseInfo(accessor.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/api/v1/members/spots")
    @MemberOnly
    public ResponseEntity<MemberSpotResponse> getMySpotInfo(@Auth final Accessor accessor) {
        final MemberSpotResponse response = memberService.getMemberSpotInfo(accessor.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/v1/members")
    @MemberOnly
    public ResponseEntity<MemberUpdateResponse> updateMyInfo(@Auth final Accessor accessor, @ModelAttribute
    MemberUpdateRequest request) {
        final MemberUpdateResponse response = memberService.updateMemberInfo(accessor.getMemberId(), request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/api/v1/members")
    @MemberOnly
    public ResponseEntity<MemberDeleteResponse> withdraw(HttpServletRequest request, @Auth final Accessor accessor) {
        final MemberDeleteResponse response = memberService.withdraw(accessor.getMemberId());
        request.getSession().invalidate();
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/api/v1/members/{memberId}")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable("memberId") final Long memberId) {
        MemberInfoResponse response = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/members/followers/{memberId}")
    public ResponseEntity<MemberFollowerListResponse> getMemberFollowerList(@PathVariable("memberId") final Long memberId) {
        MemberFollowerListResponse response = memberService.getFollowerList(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/members/followings/{memberId}")
    public ResponseEntity<MemberFollowingListResponse> getMemberFollowingList(@PathVariable("memberId") final Long memberId) {
        MemberFollowingListResponse response = memberService.getFollowingList(memberId);
        return ResponseEntity.ok(response);
    }
}
