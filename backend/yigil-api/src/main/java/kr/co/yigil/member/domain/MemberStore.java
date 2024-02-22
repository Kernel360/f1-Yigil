package kr.co.yigil.member.domain;

import kr.co.yigil.member.Member;

public interface MemberStore {

    public void withdrawal(Long memberId);

    public Member save(Member member);
}
