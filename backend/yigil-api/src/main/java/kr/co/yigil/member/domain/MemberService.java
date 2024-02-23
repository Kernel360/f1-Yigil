package kr.co.yigil.member.domain;

import kr.co.yigil.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface MemberService {
    Member registerMember(MemberCommand.RegisterMemberRequest request);

    Member retrieveMember(Long memberId);

    void withdrawal(Long memberId);

    Member updateMemberInfo(Long memberId, MemberCommand.MemberUpdateRequest request);

    MemberInfo.MemberCourseResponse retrieveCourseList(Long memberId, Pageable pageable, String selected);
    MemberInfo.MemberSpotResponse retrieveSpotList(Long memberId, Pageable pageable, String selected);
}
