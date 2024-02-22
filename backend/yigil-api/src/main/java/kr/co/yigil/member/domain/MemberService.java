package kr.co.yigil.member.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.interfaces.dto.request.MemberUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public interface MemberService {

    Member registerMember(MemberCommand.RegisterMemberRequest request);

    Member retrieveMember(Long memberId);

    void withdrawal(Long memberId);

    Member updateMemberInfo(Long memberId, MemberUpdateRequest request);
}
