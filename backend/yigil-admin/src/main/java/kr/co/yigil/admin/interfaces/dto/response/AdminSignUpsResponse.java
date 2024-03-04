package kr.co.yigil.admin.interfaces.dto.response;

import kr.co.yigil.admin.interfaces.dto.AdminSignUpInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminSignUpsResponse {
    private Page<AdminSignUpInfoDto> adminSignUps;

}
