package kr.co.yigil.member.domain;

import java.util.Optional;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;

public interface MemberReader {

    Member getMember(Long memberId);

    Optional<Member> findMemberBySocialLoginIdAndSocialLoginType(String socialLoginId, SocialLoginType socialLoginType);

    void validateMember(Long memberId);
}
