package kr.co.yigil.member.presentation;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.dto.response.MemberDeleteResponse;
import kr.co.yigil.member.dto.response.MemberInfoResponse;
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

    @GetMapping("/api/v1/member")
    @MemberOnly
    public ResponseEntity<MemberInfoResponse> getMyInfo(@Auth final Accessor accessor) {
        final MemberInfoResponse response = memberService.getMemberInfo(accessor.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/v1/member")
    @MemberOnly
    public ResponseEntity<MemberUpdateResponse> updateMyInfo(@Auth final Accessor accessor, @ModelAttribute
            MemberUpdateRequest request) {
        final MemberUpdateResponse response = memberService.updateMemberInfo(accessor.getMemberId(), request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/api/v1/member")
    @MemberOnly
    public ResponseEntity<MemberDeleteResponse> withdraw(HttpServletRequest request, @Auth final Accessor accessor) {
        final MemberDeleteResponse response = memberService.withdraw(accessor.getMemberId());
        request.getSession().invalidate();
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/api/v1/member/{member_id}")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable("member_id") final Long memberId) {
        MemberInfoResponse response = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(response);
    }


}
