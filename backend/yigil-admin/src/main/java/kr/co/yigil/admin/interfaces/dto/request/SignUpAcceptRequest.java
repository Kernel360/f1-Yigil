package kr.co.yigil.admin.interfaces.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpAcceptRequest {
    private List<Long> ids;
}
