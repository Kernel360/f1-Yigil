package kr.co.yigil.admin.interfaces.dto.response;

import kr.co.yigil.admin.domain.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfoResponse {

    private String username;
    private String profileUrl;

    public static AdminInfoResponse from(Admin admin) {
        return new AdminInfoResponse(admin.getNickname(), admin.getProfileImageUrl());
    }
}
