package kr.co.yigil.member.presentation;

import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.dto.response.MemberUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/api/v1/member/{member_id}")
    public ResponseEntity<MemberUpdateResponse> updateMemberInfo(
            @PathVariable("member_id") final Long memberId,
            @RequestBody MemberUpdateRequest request
    ) {
        MemberUpdateResponse response = memberService.updateMemberInfo(memberId, request);
        return ResponseEntity.ok(response);
    }
}
