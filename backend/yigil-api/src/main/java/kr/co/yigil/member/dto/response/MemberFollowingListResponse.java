package kr.co.yigil.member.dto.response;

import java.util.List;
import kr.co.yigil.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberFollowingListResponse {

    private List<Member> followingList;
}
