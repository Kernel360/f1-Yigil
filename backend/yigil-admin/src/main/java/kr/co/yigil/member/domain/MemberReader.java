package kr.co.yigil.member.domain;

import kr.co.yigil.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberReader {

    Member getMemberRegardlessOfStatus(Long memberId);

    Page<Member> getMemberPageRegardlessOfStatus(Pageable pageable);
}
