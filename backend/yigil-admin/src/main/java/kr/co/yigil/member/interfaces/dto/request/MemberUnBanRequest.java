package kr.co.yigil.member.interfaces.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUnBanRequest {
    private List<Long> ids;

}
