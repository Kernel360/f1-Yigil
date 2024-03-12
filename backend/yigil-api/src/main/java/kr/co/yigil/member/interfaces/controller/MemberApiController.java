package kr.co.yigil.member.interfaces.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.member.application.MemberFacade;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.interfaces.dto.MemberDto;
import kr.co.yigil.member.interfaces.dto.mapper.MemberDtoMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {

    private final MemberFacade memberFacade;
    private final MemberDtoMapper memberDtoMapper;

    @GetMapping
    @MemberOnly
    public ResponseEntity<MemberDto.Main> getMyInfo(@Auth final Accessor accessor) {
        var memberInfo = memberFacade.getMemberInfo(accessor.getMemberId());
        var response = memberDtoMapper.of(memberInfo);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<MemberDto.MemberUpdateResponse> updateMyInfo(
        @Auth final Accessor accessor,
        @ModelAttribute MemberDto.MemberUpdateRequest request
    ) {
        var memberCommand = memberDtoMapper.of(request);
        var message = memberFacade.updateMemberInfo(accessor.getMemberId(),
            memberCommand);
        var response = memberDtoMapper.of(message);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    @MemberOnly
    public ResponseEntity<MemberDto.MemberDeleteResponse> withdraw(HttpServletRequest request,
        @Auth final Accessor accessor) {
        var responseInfo = memberFacade.withdraw(accessor.getMemberId());
        var response = memberDtoMapper.of(responseInfo);
        request.getSession().invalidate();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto.Main> getMemberInfo(
        @PathVariable("memberId") final Long memberId) {
        var memberInfo = memberFacade.getMemberInfo(memberId);
        var response = memberDtoMapper.of(memberInfo);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nickname_duplicate_check")
    public ResponseEntity<MemberDto.NicknameCheckResponse> nicknameDuplicateCheck(
        @RequestBody MemberDto.NicknameCheckRequest request) {
        MemberInfo.NicknameCheckInfo checkInfo = memberFacade.nicknameDuplicateCheck(request.getNickname());
        MemberDto.NicknameCheckResponse response = memberDtoMapper.of(checkInfo);
        return ResponseEntity.ok().body(response);
    }
}
