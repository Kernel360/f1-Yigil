package kr.co.yigil.member.interfaces.dto.response;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembersResponse {
    private Page<MemberInfoDto> members;
}
