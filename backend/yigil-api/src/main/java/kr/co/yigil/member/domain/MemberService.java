package kr.co.yigil.member.domain;

import org.springframework.stereotype.Component;

@Component
public interface MemberService {

    MemberInfo.Main retrieveMemberInfo(Long memberId);

    void withdrawal(Long memberId);

    void updateMemberInfo(Long memberId, MemberCommand.MemberUpdateRequest request);

	MemberInfo.NicknameCheckInfo nicknameDuplicateCheck(String nickname);
}
