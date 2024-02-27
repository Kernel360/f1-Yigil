package kr.co.yigil.member.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface MemberService {

    MemberInfo.Main retrieveMemberInfo(Long memberId);

    void withdrawal(Long memberId);

    boolean updateMemberInfo(Long memberId, MemberCommand.MemberUpdateRequest request);

    MemberInfo.FollowerResponse getFollowerList(Long memberId, Pageable pageable);
    MemberInfo.FollowingResponse getFollowingList(Long memberId, Pageable pageable);

}
