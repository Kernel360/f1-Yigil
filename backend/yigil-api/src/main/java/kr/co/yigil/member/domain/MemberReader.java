package kr.co.yigil.member.domain;

import kr.co.yigil.member.Member;

public interface MemberReader {

    Member getMember(Long memberId);

    void validateMember(Long memberId);
}
