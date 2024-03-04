package kr.co.yigil.member.infrastructure;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;

import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {
    private final MemberRepository memberRepository;
    @Override
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() ->new BadRequestException(NOT_FOUND_MEMBER_ID));
    }

    @Override
    public Optional<Member> findMemberBySocialLoginIdAndSocialLoginType(String socialLoginId,
            SocialLoginType socialLoginType) {
        return memberRepository.findMemberBySocialLoginIdAndSocialLoginType(socialLoginId, socialLoginType);
    }

    public void validateMember(Long memberId) {
        if(!memberRepository.existsById(memberId)){
            throw new BadRequestException(NOT_FOUND_MEMBER_ID);
        }
    }
}
