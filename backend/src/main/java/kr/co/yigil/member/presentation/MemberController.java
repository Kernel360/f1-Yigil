package kr.co.yigil.member.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.dto.response.MemberDeleteResponse;
import kr.co.yigil.member.dto.response.MemberInfoResponse;
import kr.co.yigil.member.dto.response.MemberUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/api/v1/member")
    @MemberOnly
    public ResponseEntity<MemberInfoResponse> getMyInfo(@Auth final Accessor accessor) {
        final MemberInfoResponse response = memberService.getMemberInfo(accessor.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/api/v1/member")
    @MemberOnly
    public ResponseEntity<MemberUpdateResponse> updateMyInfo(@Auth final Accessor accessor, @RequestBody
            MemberUpdateRequest request) {
        final MemberUpdateResponse response = memberService.updateMemberInfo(accessor.getMemberId(), request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/api/v1/member")
    @MemberOnly
    public ResponseEntity<MemberDeleteResponse> withdraw(@Auth final Accessor accessor) {
        return ResponseEntity.ok().body(new MemberDeleteResponse());
    }


    @GetMapping("/api/v1/member/{member_id}")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable("member_id") final Long memberId) {
        MemberInfoResponse response = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test")
    public ResponseEntity<String> testUploadFile(@RequestParam("file")MultipartFile file) {
        FileUploadEvent event = new FileUploadEvent(this, file, System.out::println);
        applicationEventPublisher.publishEvent(event);
        return ResponseEntity.ok().body(file.getOriginalFilename());
    }
}
