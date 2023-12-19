package kr.co.yigil.member.application;

import kr.co.yigil.member.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.dto.response.MemberUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    public MemberUpdateResponse updateMemberInfo(Long memberId, MemberUpdateRequest request) {
        return new MemberUpdateResponse();
    }
}
