package kr.co.yigil.member.infrastructure;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {

    private final MemberRepository memberRepository;

    @Override
    public Member getMemberRegardlessOfStatus(Long memberId) {
        return memberRepository.findByIdRegardlessOfStatus(memberId)
                .orElseThrow(() ->new BadRequestException(NOT_FOUND_MEMBER_ID));
    }

    @Override
    public Page<Member> getMemberPageRegardlessOfStatus(Pageable pageable) {
        return memberRepository.findAllMembersRegardlessOfStatus(pageable);
    }
}
