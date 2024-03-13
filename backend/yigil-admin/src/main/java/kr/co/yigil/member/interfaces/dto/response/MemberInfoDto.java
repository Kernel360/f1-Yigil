package kr.co.yigil.member.interfaces.dto.response;

import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.member.MemberStatus;
import kr.co.yigil.member.SocialLoginType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MemberInfoDto {
    private final Long memberId;
    private final String nickname;
    private final String profileImageUrl;
    private final MemberStatus status;
    private final SocialLoginType socialLoginType;
    private final Ages ages;
    private final Gender gender;
    private final String email;
    private final String joinedAt;
}
