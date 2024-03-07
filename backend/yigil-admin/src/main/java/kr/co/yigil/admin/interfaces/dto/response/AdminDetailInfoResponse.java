package kr.co.yigil.admin.interfaces.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDetailInfoResponse {

    private String nickname;
    private String profileUrl;
    private String email;
    private String password;

}
