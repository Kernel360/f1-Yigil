package kr.co.yigil.admin.interfaces.dto.request;

import kr.co.yigil.admin.domain.adminSignUp.AdminSignUp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminSignupRequest {
    private String email;
    private String nickname;
}
