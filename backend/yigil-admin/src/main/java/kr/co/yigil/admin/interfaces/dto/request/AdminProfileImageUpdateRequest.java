package kr.co.yigil.admin.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProfileImageUpdateRequest {
    private MultipartFile profileImageFile;

}
