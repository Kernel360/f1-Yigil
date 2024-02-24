package kr.co.yigil.member.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberCommand.TravelsVisibilityRequest;
import kr.co.yigil.member.domain.MemberInfo.TravelsVisibilityResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface MemberService {

    Member retrieveMember(Long memberId);
    MemberInfo.Main retrieveMemberInfo(Long memberId);

    void withdrawal(Long memberId);

    void updateMemberInfo(Long memberId, MemberCommand.MemberUpdateRequest request);

    MemberInfo.MemberCourseResponse retrieveCourseList(Long memberId, Pageable pageable, String selected);
    MemberInfo.MemberSpotResponse retrieveSpotList(Long memberId, Pageable pageable, String selected);

    MemberInfo.FollowerResponse getFollowerList(Long memberId, Pageable pageable);
    MemberInfo.FollowingResponse getFollowingList(Long memberId, Pageable pageable);

    TravelsVisibilityResponse setTravelsVisibility(Long memberId, TravelsVisibilityRequest memberCommand);
}
