package kr.co.yigil.member.infrastructure;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberStore;
import kr.co.yigil.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {

    private final MemberRepository memberRepository;

    @Override
    public void withdrawal(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }
}
