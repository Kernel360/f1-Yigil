package kr.co.yigil.login.domain;

import kr.co.yigil.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class LoginCommand {

    @Getter
    @Builder
    @ToString
    public static class LoginRequest {
        private final String id;
        private final String nickname;
        private final String profileImageUrl;
        private final String email;
        private final String provider;

        public Member toEntity(String providerName) {
            return new Member(
                    email,
                    id,
                    nickname,
                    profileImageUrl,
                    providerName
            );
        }
    }


}
