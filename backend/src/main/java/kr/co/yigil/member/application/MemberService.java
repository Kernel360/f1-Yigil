package kr.co.yigil.member.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.member.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.dto.response.MemberInfoResponse;
import kr.co.yigil.member.dto.response.MemberUpdateResponse;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public MemberInfoResponse getMemberInfo(final Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));
        List<Post> postList = postRepository.findAllByMember(member);
        return MemberInfoResponse.from(member, postList);
    }

    public MemberUpdateResponse updateMemberInfo(final Long memberId, MemberUpdateRequest request) {
        return new MemberUpdateResponse();
    }
}
