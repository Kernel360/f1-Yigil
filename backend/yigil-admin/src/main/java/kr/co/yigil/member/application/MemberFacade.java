package kr.co.yigil.member.application;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberService;
import kr.co.yigil.member.interfaces.dto.request.MemberBanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;

    public Page<Member> getMemberPage(Pageable pageable) {
        return memberService.getMemberPage(pageable);
    }

    public void banMembers(MemberBanRequest request) {
        memberService.banMembers(request);
    }

    public void unbanMembers(MemberBanRequest request) {
        memberService.unbanMembers(request);
    }
}
