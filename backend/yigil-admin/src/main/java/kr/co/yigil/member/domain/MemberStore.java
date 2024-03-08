package kr.co.yigil.member.domain;

import kr.co.yigil.member.Member;

public interface MemberStore {

    public void deleteMember(Long memberId);

    public Member save(Member member);

    void banMember(Long memberId);

    void unbanMember(Long memberId);
}
