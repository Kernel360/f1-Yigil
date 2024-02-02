package kr.co.yigil.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
