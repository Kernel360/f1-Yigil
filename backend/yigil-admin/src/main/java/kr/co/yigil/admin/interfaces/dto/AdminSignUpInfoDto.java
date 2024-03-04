package kr.co.yigil.admin.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminSignUpInfoDto {

    private Long id;
    private String email;
    private String nickname;
    private String requestDatetime;

}
