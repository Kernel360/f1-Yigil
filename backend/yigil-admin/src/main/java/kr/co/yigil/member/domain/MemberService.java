package kr.co.yigil.member.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.interfaces.dto.request.MemberBanRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MemberService {

    Page<Member> getMemberPage(Pageable pageable);

    void banMembers(MemberBanRequest request);

    void unbanMembers(MemberBanRequest request);
}
