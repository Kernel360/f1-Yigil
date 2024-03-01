package kr.co.yigil.admin.interfaces.dto.response;

import kr.co.yigil.admin.domain.admin.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfoResponse {

    private String username;
    private String profileUrl;

}
