package kr.co.yigil.member.interfaces.dto.response;

import kr.co.yigil.member.MemberStatus;
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
    private final String joinedAt;


}
