package kr.co.yigil.member.interfaces.controller;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.application.MemberFacade;
import kr.co.yigil.member.interfaces.dto.mapper.MemberMapper;
import kr.co.yigil.member.interfaces.dto.request.MemberBanRequest;
import kr.co.yigil.member.interfaces.dto.request.MemberRequest;
import kr.co.yigil.member.interfaces.dto.response.MemberBanResponse;
import kr.co.yigil.member.interfaces.dto.response.MembersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {
    private final MemberFacade memberFacade;
    private final MemberMapper memberMapper;

    @GetMapping
    public ResponseEntity<MembersResponse> getMembers(@ModelAttribute MemberRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getDataCount());
        Page<Member> memberPage = memberFacade.getMemberPage(pageable);
        MembersResponse response = memberMapper.toResponse(memberPage);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ban")
    public ResponseEntity<MemberBanResponse> banMembers(@RequestBody MemberBanRequest request) {
        memberFacade.banMembers(request);
        return ResponseEntity.ok(new MemberBanResponse("회원 밴 완료"));
    }

    @PostMapping("/unban")
    public ResponseEntity<MemberBanResponse> unbanMembers(@RequestBody MemberBanRequest request) {
        memberFacade.unbanMembers(request);
        return ResponseEntity.ok(new MemberBanResponse("회원 밴 해제 완료"));
    }

}
