package kr.co.yigil.login.dto.response;

import lombok.Data;

@Data
public class KakaoTokenInfoResponse {
    private Long id;
    private int expiresIn;
    private int appId;
}
