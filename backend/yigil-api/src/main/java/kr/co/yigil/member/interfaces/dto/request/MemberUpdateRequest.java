package kr.co.yigil.member.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {

    private String nickname;
    private MultipartFile profileImageFile;
    private String ages;
    private String gender;
}
