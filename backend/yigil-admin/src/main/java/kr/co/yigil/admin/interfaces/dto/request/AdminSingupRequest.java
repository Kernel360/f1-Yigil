package kr.co.yigil.admin.interfaces.dto.request;

import kr.co.yigil.admin.domain.AdminSignUp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminSingupRequest {
    private String email;
    private String nickname;

    public AdminSignUp toEntity() {
        return new AdminSignUp(email, nickname);
    }
}
