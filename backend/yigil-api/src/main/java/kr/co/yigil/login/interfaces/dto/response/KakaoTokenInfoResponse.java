package kr.co.yigil.login.interfaces.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoTokenInfoResponse {
    private Long id;
    private int expiresIn;
    private int appId;
}
