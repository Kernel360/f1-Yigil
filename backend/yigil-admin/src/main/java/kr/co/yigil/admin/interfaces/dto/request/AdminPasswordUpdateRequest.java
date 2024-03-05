package kr.co.yigil.admin.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPasswordUpdateRequest {
    private String existingPassword;
    private String newPassword;

}
