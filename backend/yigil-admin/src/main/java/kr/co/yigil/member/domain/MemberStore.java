package kr.co.yigil.member.domain;

import java.util.List;

public interface MemberStore {

    void banMember(Long memberId);

    void unbanMember(Long memberId);
}
